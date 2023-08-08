package com.dazhou;

import com.dazhou.project.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author shkstart
 * @create 2023-08-02 9:53
 */
@SpringBootTest
public class invokeCountTest {
    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void Testcc(){
        boolean b = userInterfaceInfoService.invokeCount(1L, 1L);
        System.err.println(b);
    }
}
