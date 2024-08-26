package com.tenco.smtp.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    private Map<String, String> validatedCode = new HashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private HttpSession session;
    /**
     * 이메일을 보내는 메소드
     * @param to = 수신자
     * @param subject = 제목
     * @param text = 내용
     */
    public void sendMail(String to, String subject, String text) {
        try {
            // eeeeeeeeeeeeeeee
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
            validatedCode.put("validatedCode", buffer.toString());
        	System.out.println("Random Code : " + buffer);
        	return buffer.toString();
    }

    /**
     * 유효 시간 초과 시 String 값(인증코드)를 삭제시키는 메소드입니두
     * @param delay = 단위 앞에 있는 숫자를 의미합니두
     * @param timeUnit = 단위를 설정합니다. 초, 분 같은걸루다가
     */
    public void expiredCode(long delay, TimeUnit timeUnit) {
        scheduler.schedule(() -> {
            session.removeAttribute("validatedCode");
            System.out.println("이거 지웁니다 : " + validatedCode);
        }, delay, timeUnit);
    }



    /**
     * 비밀번호 재설정 페이지를 보내주는 메소드
     * @param to
     * @param subject
     * @param text
     */
    public void sendResetPasswordPage(String to, String subject, String text) {

    }

}