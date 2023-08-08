package com.dazhou.dzapiinterface.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 染色数据拦截器
 */
public class RanSeInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的染色数据
        String dyeData = request.getHeader("ranse");

        if (dyeData == null || !dyeData.equals("dazhou")) {
            // 如果染色数据不存在或者不匹配，则返回错误响应
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // 继续向下执行
        return true;
    }
}
