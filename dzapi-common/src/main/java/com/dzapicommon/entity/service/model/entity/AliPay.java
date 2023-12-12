package com.dzapicommon.entity.service.model.entity;

import lombok.Data;

/**
 * @author <a href="https://github.com/Dazhou-del">Dazhou</a>
 * @create 2023-12-10 16:11
 */
@Data
public class AliPay {
    private String traceNo;
    private Double totalAmount;
    private String subject;
    private String alipayTraceNo;
}