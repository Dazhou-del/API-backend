package com.dazhou.project.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.dazhou.project.config.AliPayConfig;
import com.dazhou.project.exception.BusinessException;
import com.dazhou.project.service.InterfaceInfoService;
import com.dazhou.project.service.OrderPayService;
import com.dazhou.project.service.UserInterfaceInfoService;
import com.dazhou.project.service.UserService;
import com.dazhou.project.utils.OrderNumber;
import com.dzapicommon.common.ErrorCode;
import com.dzapicommon.entity.service.model.entity.InterfaceInfo;
import com.dzapicommon.entity.service.model.entity.OrderPay;
import com.dzapicommon.entity.service.model.entity.User;
import com.dzapicommon.entity.service.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-10 16:10
 */
@Controller
@RequestMapping("/alipay")
public class AliPayController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private OrderPayService orderPayService;
    @Resource
    AliPayConfig aliPayConfig;
    private static final String GATEWAY_URL ="https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    private static final String FORMAT ="JSON";
    private static final String CHARSET ="utf-8";
    private static final String SIGN_TYPE ="RSA2";

    @GetMapping("/pay")
    @ResponseBody// &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(Long interfaceInfoId, HttpServletResponse httpResponse,HttpServletRequest request) throws Exception {
        if (interfaceInfoId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
//        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        Long interfaceInfoIds = userInterfaceInfo.getInterfaceInfoId();

        // 校验
        if (interfaceInfoId<0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoId);
        //看接口状态是否开启
        Boolean  checkResult=interfaceInfoService.checkInterfaceStatus(interfaceInfoId);
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfo.setUserId(loginUser.getId());

        //生成订单
        OrderPay orderPay = new OrderPay();
        //生成随机订单号
        String generateOrderNumber = OrderNumber.generateOrderNumber();
        orderPay.setOrderId(generateOrderNumber);
        //总金额
        Integer TotalAmount=10;
        orderPay.setPayPrice(TotalAmount);
        orderPay.setPayMethod("支付宝");
        orderPay.setUserName(loginUser.getUserName());
        orderPay.setGoodsName(interfaceInfo.getName());
        orderPay.setStatus(0);
        orderPayService.save(orderPay);
        //设置次数和状态
        userInterfaceInfo.setLeftNum(1000);
        userInterfaceInfo.setTotalNum(0);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);

        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceInfoId = userInterfaceInfo.getId();
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAlipayPublicKey(), SIGN_TYPE);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        alipayTradePagePayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());
        alipayTradePagePayRequest.setBizContent("{\"out_trade_no\":\"" + generateOrderNumber + "\","
                + "\"total_amount\":\"" +  String.valueOf(TotalAmount) + "\","
                + "\"subject\":\"" + interfaceInfo.getName() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayTradePagePayRequest).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


    /*@RequestMapping(value = "/pay" ,produces = "text/html; charset=UTF-8") // http://localhost:7529/api/alipay/pay?subject=wdw&traceNo=121313123&totalAmount=1000
    @ResponseBody
    public String pay(Long interfaceInfoId, HttpServletRequest request) {
        if (interfaceInfoId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
//        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        Long interfaceInfoIds = userInterfaceInfo.getInterfaceInfoId();

        // 校验
        if (interfaceInfoId<0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"接口不存在");
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(interfaceInfoId);
        //看接口状态是否开启
        Boolean  checkResult=interfaceInfoService.checkInterfaceStatus(interfaceInfoId);
        User loginUser = userService.getLoginUser(request);
        userInterfaceInfo.setUserId(loginUser.getId());

        //生成订单
        OrderPay orderPay = new OrderPay();
        //生成随机订单号
        String generateOrderNumber = OrderNumber.generateOrderNumber();
        orderPay.setOrderId(generateOrderNumber);
        //总金额
        Integer TotalAmount=10;
        orderPay.setPayPrice(TotalAmount);
        orderPay.setPayMethod("支付宝");
        orderPay.setUserName(loginUser.getUserName());
        orderPay.setGoodsName(interfaceInfo.getName());
        orderPay.setStatus(0);
        orderPayService.save(orderPay);
        //设置次数和状态
        userInterfaceInfo.setLeftNum(1000);
        userInterfaceInfo.setTotalNum(0);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);

        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newUserInterfaceInfoId = userInterfaceInfo.getId();

        AlipayTradePagePayResponse response;

        try {
            //  发起API调用（以创建当面付收款二维码为例）
            response = Factory.Payment.Page()
                    .pay(URLEncoder.encode(interfaceInfo.getName(), "UTF-8"), generateOrderNumber, String.valueOf(TotalAmount), "");
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println(response);
        return response.getBody();
    }*/

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            String sign = params.get("sign");
            //sdk验签
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, aliPayConfig.getAlipayPublicKey(), "UTF-8"); // 验证签名
//            response.setContentType("text/html;charset=utf-8");
//            PrintWriter out = response.getWriter();
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));

                // 更新订单未已支付
                LambdaUpdateWrapper<OrderPay> orderPayUpdateWrapper = new LambdaUpdateWrapper<>();
                orderPayUpdateWrapper.eq(OrderPay::getOrderId,tradeNo);
                orderPayUpdateWrapper.setSql("status=1");
                orderPayService.update(orderPayUpdateWrapper);
//                orderPayService.updateState(tradeNo, "已支付", gmtPayment, alipayTradeNo);
            }
        }
        return "success";
    }

}
