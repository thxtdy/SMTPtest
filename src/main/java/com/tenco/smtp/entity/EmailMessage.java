package com.tenco.smtp.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    private String to;      // 수신자
    private String subject; // 제목
    private String text; // 내용

}