package com.tenco.smtp.controller;

import com.tenco.smtp.service.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("/send-mail")
@Controller
@RequiredArgsConstructor
public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private HttpSession session;
	@GetMapping("/emailPage")
	public String sendEmailPage() {
		System.out.println("EmailPage 들어옴");
		return "user/sendEmail";
	}



	@GetMapping("/email/{email}")
	public ResponseEntity<Map<String, String>> sendCode(@PathVariable("email") String email) {
		System.out.println("AJAX 매핑");
		System.out.println("이메일입니다 : " + email);

		Map<String, String> response = new HashMap<>();

		// 이메일 유효성 검사
		if (email == null || email.trim().isEmpty()) {
			System.out.println("빈칸에 들어왔습니다.");
			response.put("message", "E-mail을 입력해주세요.");
        	return ResponseEntity.badRequest().body(response);
		}
        // 이메일 형식 검증 (간단한 정규 표현식 사용)
        if (!email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+\\.[a-zA-Z]{2,}$")) {
			System.out.println("유효하지 않은 곳에 들어왔습니다.");
			response.put("message", "유효하지 않은 E-mail입니다.");
			return ResponseEntity.badRequest().body(response);
        }

			String code = emailService.createCode();
			emailService.sendMail(email, "이메일 인증번호", code);
			session.setAttribute("validateCode", code);

		response.put("message", "인증 코드를 발송하였습니다.");
		emailService.expiredCode(5, TimeUnit.SECONDS);
		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/checkCode/{enteredCode}")
	public ResponseEntity<Map<String, String>> checkCode(@PathVariable("enteredCode") String enteredCode) {
		System.out.println("AJAX check 매핑");
		String validateCode = (String) session.getAttribute("validateCode");
		System.out.println("Validated Code : " + validateCode);
		Map<String, String> response = new HashMap<>();

		if(enteredCode.trim().isEmpty() || enteredCode.equals("")) {
			response.put("message","인증 코드를 입력해주세요.");
			return ResponseEntity.badRequest().body(response);
		}
		
		if(!enteredCode.equals(validateCode)) {
			response.put("message", "인증코드가 일치하지 않습니다.");
			return ResponseEntity.badRequest().body(response);
		}
		
			response.put("message", "인증에 성공하였습니다.");
			return ResponseEntity.ok(response);



	}


	

}