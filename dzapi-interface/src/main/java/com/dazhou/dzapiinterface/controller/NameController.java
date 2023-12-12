package com.dazhou.dzapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 名称 API
 * 查询名称接口
 * @author dazhou
 */
@RestController
@RequestMapping("/api/name")
public class NameController {


    @PostMapping("/user")
    public String getUsernameByPost(HttpServletRequest request, HttpServletResponse response) {
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
//
//        return body;
        if (body.equals(body.toLowerCase())) {
            // 字符串是小写，转换为大写
            body = body.toUpperCase();
            return body;
        } else if (body.equals(body.toUpperCase())) {
            // 字符串是大写，转换为小写
            body = body.toLowerCase();
            return body;
        } else {
            return null;
        }
    }
}
