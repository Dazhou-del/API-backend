package com.dazhou.project.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * 生成随机订单号
 * @create 2023-12-10 17:40
 */
public class OrderNumber {
    public static String generateOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;

        return timestamp + randomNumber;
    }

}
