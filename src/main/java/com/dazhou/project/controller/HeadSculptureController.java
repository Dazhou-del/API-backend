package com.dazhou.project.controller;

import com.dzapicommon.common.BaseResponse;
import com.dzapicommon.common.ResultUtils;
import com.dazhou.project.template.OssTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dazhou
 * @create 2023-08-06 21:11
 */
@RestController
public class HeadSculptureController {
    @Autowired
    private OssTemplate ossTemplate;

    @PostMapping("/file/upload")
    public BaseResponse<Map<String,Object>> upload(@RequestBody  MultipartFile file,
                                                   HttpServletRequest request)throws IOException {
        Map<String, Object> result = new HashMap<>(2);
        // 上传并返回新文件名称
        String filename = file.getOriginalFilename();
        file.getInputStream();
        String imageUrl = ossTemplate.upload(file.getOriginalFilename(), file.getInputStream());
//        String imageUrl="'ssc.jpg";
        result.put("url", imageUrl);
        System.err.println(imageUrl);
        return ResultUtils.success(result);


    }
}
