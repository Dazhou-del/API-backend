package com.dazhou.dzapigateway.filter;

import com.dazhou.dazhouclientsdk.util.SignUtils;
import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.entity.service.InnerInterfaceInfoService;
import com.dzapicommon.entity.service.InnerUserInterfaceInfoService;
import com.dzapicommon.entity.service.InnerUserService;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.dzapicommon.entity.service.model.entity.User;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 全局过滤
 *
 * @author shkstart
 * @create 2023-08-02 22:30
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;
    @DubboReference
    private InnerUserService innerUserService;
    //设置超时时间，设置重试次数。
    @DubboReference(check = false,timeout = 3000,retries = 3)
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

//    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

//        public static final String INTERFACE_HOST="http://localhost:8123";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 用户发起请求到API网关  已经实现
        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        //路径
        String url = request.getPath().value();
        URI uri = request.getURI();
        String s = uri.getHost().toString();
        int port = uri.getPort();
        //http
        String scheme = uri.getScheme();
//        协议+ip+端口号
        String host = scheme + "://" + s + ":" + port;
        String path = host + url;
        String method = request.getMethod().toString();
        log.info("请求唯一标识: " + request.getId());
        log.info("请求路径: " + path);
        log.info("请求方法: " + method);
        log.info("请求参数: " + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址: " + sourceAddress);
        log.info("请求来源地址: " + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();
        //3.（黑白名单） 如果来源的地址不在白名单中，就返回一个状态码
//        if (!IP_WHITE_LIST.contains(sourceAddress)) {
//            return handleNotAuth(response);
//        }
        //4. 用户鉴权（判断ak, sk是否合法）
        HttpHeaders headers = request.getHeaders();

        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        // 实际情况应该是去数据库中查是否已经分配给用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNotAuth(response);
        }
        if (StringUtils.isEmpty(nonce) || StringUtils.isEmpty(sign)
                || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(method)) {
            return handleNotAuth(response);
        }
        //禁止重发攻击
        String redisNonce = (String) redisTemplate.opsForValue().get(nonce);
        if (!StringUtils.isEmpty(redisNonce)) {
            return handleNotAuth(response);
        }
        if (Long.parseLong(nonce) > 100000000) {
            return handleNotAuth(response);
        }
        //不超过5分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES)) {
            return handleNotAuth(response);
        }

        // 实际情况应该是去数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serveSign = SignUtils.getSign(body, secretKey);
        if (sign == null || !sign.equals(serveSign)) {
            return handleNotAuth(response);
        }
        //5. 请求的模拟接口是否存在 模拟接口是否存在，以及请求方法是否匹配，(还可以校验请求参数)
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(host, url, method);
        } catch (Exception e) {
            log.error("interfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleNotAuth(response);
        }
        log.info("到查看是否还要调用次数");
        // 是否还有调用次数
        Long interfaceInfoId = interfaceInfo.getId();
        Long userId = invokeUser.getId();
        UserInterfaceInfo userInterfaceInfo = innerUserInterfaceInfoService.getLeftNum(interfaceInfoId, userId);
        Integer leftNum = userInterfaceInfo.getLeftNum();
        if (leftNum.intValue() <= 0) {
            log.error("没有调用次数了");
            return response.setComplete();
        }
        //7.请求转发，调用模拟接口+响应日志
        return handleResponse(exchange, chain, interfaceInfoId, userId);

    }


    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {

                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                            log.info("接口调查次+1后");
                                            ServerHttpRequest request = exchange.getRequest();
                                            String nonce = request.getHeaders().getFirst("nonce");
                                            redisTemplate.opsForValue().set(nonce, 1, 5, TimeUnit.MINUTES);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                // 流量染色，只有染色数据才能被调用
                ServerHttpRequest httpRequest = exchange.getRequest().mutate()
                        .header("ranse", "dazhou").build();
                return chain.filter(exchange.mutate().request(httpRequest)
                        .response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }


    //公共方法没有权限
    public Mono<Void> handleNotAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }


//    使用Redisson分布式锁来实现操作互斥。用方法名+用户id上锁。
    /*private void postHandler(ServerHttpRequest request, ServerHttpResponse response, Long interfaceInfoId, Long userId) {
        RLock lock = redissonClient.getLock("api:add_interface_num:" + userId);
        if (response.getStatusCode() == HttpStatus.OK) {
            CompletableFuture.runAsync(() -> {
                if (lock.tryLock()) {
                    try {
                        addInterfaceNum(request, interfaceInfoId, userId);
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }
    }*/

    /*private void addInterfaceNum(ServerHttpRequest request, Long interfaceInfoId, Long userId) {
        String nonce = request.getHeaders().getFirst("nonce");
        if (StringUtil.isEmpty(nonce)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求重复");
        }
        UserInterfaceInfo userInterfaceInfo = innerUserInterfaceInfoService.hasLeftNum(interfaceInfoId, userId);
        // 接口未绑定用户
        if (userInterfaceInfo == null) {
            Boolean save = innerUserInterfaceInfoService.addDefaultUserInterfaceInfo(interfaceInfoId, userId);
            if (save == null || !save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口绑定用户失败！");
            }
        }
        if (userInterfaceInfo != null && userInterfaceInfo.getLeftNum() <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用次数已用完！");
        }
        redisTemplate.opsForValue().set(nonce, 1, 5, TimeUnit.MINUTES);
        //调用成功，接口调用次数 + 1 invokeCount
        innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }*/

}
