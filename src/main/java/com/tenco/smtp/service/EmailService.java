package com.tenco.smtp.service;

import java.security.SecureRandom;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    /**
     * 이메일을 보내는 메소드
     * @param to = 수신자
     * @param subject = 제목
     * @param text = 내용
     */
    public void sendMail(String to, String subject, String text) {
        try {
        	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(text, true);
            // namcher9428
            javaMailSender.send(mimeMessage);
    		
        } catch (MessagingException e) {
            throw new RuntimeException("Email을 보내는데에 오류가 생겼습니다.", e);
        }
    }

    /**
     * 이메일 인증 코드 메소드
     * @return buffer.toString(); => SecureRandom으로 만들어진 코드
     */
    public String createCode() {
    	final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        	SecureRandom random = new SecureRandom();
        	StringBuffer buffer = new StringBuffer();
        	for (int i = 0; i < 6; i++) { // chars 를 이용해 6자리 난수 생성
        		int randomIndex = random.nextInt(chars.length());
        		buffer.append(chars.charAt(randomIndex));

            // namcher9428
    		
        	}
        	System.out.println("Random Code : " + buffer);
        	return buffer.toString();
    }

}