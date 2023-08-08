package com.dazhou.dzapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import com.codeinchinese.英汉词典.英汉词典;
import com.codeinchinese.英汉词典.词条;
import com.dazhou.dazhouclientsdk.model.User;
import com.dazhou.dzapiinterface.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 名称 API
 * 查询名称接口
 * @author dazhou
 */
@RestController
@RequestMapping("/api/name")
public class NameController {


    @PostMapping("/user")
    public String getUsernameByPost(HttpServletRequest request, HttpServletResponse response){
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        if (body==null){
            throw  new BusinessException(4000,"单词无法解析，参数有误");
        }
        String string= null;
        try {
            string = 英汉词典.查词(body).toString();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
//            throw  new BusinessException(4000,"单词无法解析，请查看单词是否输入错误");

        }
        return string;
    }
}
