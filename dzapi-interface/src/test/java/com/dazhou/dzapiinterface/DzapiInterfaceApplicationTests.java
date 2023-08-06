package com.dazhou.dzapiinterface;

import com.codeinchinese.英汉词典.英汉词典;
import com.dazhou.dazhouclientsdk.client.RzApiClient;
import com.dazhou.dazhouclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DzapiInterfaceApplicationTests {
    @Resource
    private RzApiClient rzApiClient;

    @Test
    void contextLoads() {
        String dazhou = rzApiClient.getNameByGet("dazhou");
        User user = new User();
        user.setUsername("sad");
        String userNameByPost = rzApiClient.getUserNameByPost(user);
        System.out.println(dazhou);
        System.out.println(userNameByPost);
    }

    @Test
    public void ww(){
        String string = 英汉词典.查词("school").toString();
        System.err.println(string);
    }

}
