package com.dazhou.dzapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.codeinchinese.英汉词典.英汉词典;
import com.codeinchinese.英汉词典.词条;
import com.dazhou.dazhouclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 * 查询名称接口
 * @author JinZe
 */
@RestController
@RequestMapping("/api/name")
public class NameController {


    @PostMapping("/user")
    public String getUsernameByPost(HttpServletRequest request ) {
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        String string = null;
        try {
            string = 英汉词典.查词(body).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "单词有误";
        }
        return string;
    }
}
