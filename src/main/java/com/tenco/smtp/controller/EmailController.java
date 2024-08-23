package com.tenco.smtp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenco.smtp.service.EmailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/send-mail")
@Controller
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;
	@Autowired
	HttpSession session;
	@GetMapping("/emailPage")
	public String sendEmailPage() {
		System.out.println("EmailPage 들어옴");
		return "user/sendEmail";
	}

	// 비밀번호 재설정 링크
//    @PostMapping("/password")
//    public ResponseEntity sendPasswordMail(@RequestBody EmailPostDTO emailPostDto) {
//        EmailMessage emailMessage = EmailMessage.builder()
//                .to(emailPostDto.getEmail())
//                .subject("[SAVIEW] 임시 비밀번호 발급")
//                .build();
//
//        emailService.sendMail(emailMessage, "password");
//
//        return ResponseEntity.ok().build();
//    }

	// 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
//    @PostMapping("/email")
//    public String sendJoinMail() {
//    	
//        emailService.sendMail("namcher9428@gmail.com", "안녕하세요", "www.naver.com");
//
//        return "Success to send Email";
//    }
	@GetMapping("/email/*")
	public void sendCode(@RequestParam("email") String email) {
		System.out.println("AJAX 매핑");
		System.out.println("이메일입니다 : " + email);
		 // 이메일 유효성 검사
//        if (email == null || email.trim().isEmpty()) {
//        	System.out.println("입력되지 않은");
//        }
//
//        // 이메일 형식 검증 (간단한 정규 표현식 사용)
//        if (!email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+\\.[a-zA-Z]{2,}$")) {
//        	System.out.println("유효하지 않은");
//        }		
		String code = emailService.createCode();
		emailService.sendMail(email, "이메일 인증번호", code);
		session.setAttribute("validateCode", code);
	}
	
	@PostMapping("/checkCode")
	public String checkCode(@RequestParam("enteredCode") String checkCode) {
		System.out.println("AJAX check 매핑");
		String validateCode = (String) session.getAttribute("validateCode");
		
		if(validateCode.equals("") || validateCode == null) {
			System.out.println("NO CODE");
		}
		
		if(!checkCode.equals(validateCode)) {
			System.out.println("Not equals");
		} 
		
		if(checkCode.equals(validateCode)) {
			System.out.println("일치합니다");
		}
		
		
		return checkCode;

	}
	

}