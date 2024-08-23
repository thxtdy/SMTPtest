package com.tenco.smtp.dto;

import lombok.Data;

@Data
public class VerifyEmailCodeDTO {
	
	private String recipient; // 수신자
	private String title;
	private String content;
	
}
