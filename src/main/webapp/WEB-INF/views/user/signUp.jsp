<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- header.jsp  -->
<%@ include file="/WEB-INF/views/layout/header.jsp"%>

<!-- start of content.jsp(xxx.jsp)   -->
<div class="col-sm-8">
	<h2>회원 가입</h2>
	<h5>Bank App에 오신걸 환영합니다</h5>
	
	<form action="/user/sign-up" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="username">username:</label>
			<input type="text" class="form-control" placeholder="Enter username" id="username" name="username" value="야스오">
		</div>
		<div class="form-group">
			<label for="pwd">Password:</label>
			<input type="password" class="form-control" placeholder="Enter password" id="pwd" name="password" value="asd123">
		</div>
		<div class="form-group">
			<label for="fullname">fullname:</label>
			<input type="text" class="form-control" placeholder="Enter fullname" id="fullname" name="fullname" value="바람">
		</div>

			<!-- 이메일 입력란 -->
		<div class="form-group">
			<label for="email">E-mail:</label>
			<input type="email" class="form-control" placeholder="Enter Email" id="email" name="email" value="qkrakdth@gmail.com">
		</div>
		
			<!-- 인증 코드 발송 버튼 -->
		<div class="d-flex justify-content-center">
			<button type="button" class="btn btn-secondary" id="emailCode" onclick="fetchData()">발송</button>
		</div>
		
			<!-- 인증코드 입력란 -->
		<div class="form-group">
			<label for="enteredCode">인증번호 :</label>
			<input type="text" class="form-control" placeholder="Enter Email CheckCode" id="enteredCode" name="enteredCode">
		</div>
			<!-- 인증코드 확인 버튼 -->
		<div class="d-flex justify-content-center">
			<button type="button" class="btn btn-secondary" id="checkCode">확인</button>
		</div>
		
		
		
		
		 <div class="custom-file">
    		<input type="file" class="custom-file-input" id="customFile" name="mFile">
    		<label class="custom-file-label" for="customFile">Choose file</label>
  		</div>
  		<div class="d-flex justify-content-center">
		<button type="submit" class="btn btn-primary mt-md-4">회원가입</button>
		</div>
		<div>
			<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=7a81e9291d6290d4715bd26635e4af2b&redirect_uri=http://localhost:8080/user/kakao">
			<!-- <img alt="" src="/images/kakaoLogin.png"></a> -->
		</div>
	</form>
	
</div>
<!-- end of col-sm-8  -->
</div>
</div>
<!-- end of content.jsp(xxx.jsp)   -->
<script>
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").on("change", function() {
  let fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});


/* $("#emailCode").on('click', function() {
	alert('인증 코드가 발송되었습니다.')
    let email = $("#email").val();

    $.ajax({
        url: "/send-mail/email", // 컨트롤러에 매핑된 URL
        type: "GET",
        contentType: "application/x-www-form-urlencoded",
        data: $.param({
            email: email
        })

    });
} */
 function fetchData() {
	fetch('http://localhost:8080/send-mail/email/{email}')
	.then(data1=> {
		return data1.json();
	})
	.then(data2=> {
		console.log(data2);
	})
	.catch(error=>{
		console.log('error : ' + error);
	})
	
}
 
$("#checkCode").on('click', function() {
	
	let enteredCode = $("#enteredCode").val();
	
	$.ajax({
		url: "/send-mail/checkCode",
		type: "POST",
		contentType: "application/x-www-form-urlencoded",
		data: $.param({
			enteredCode: enteredCode
		})
		
	});
});
</script>
<!-- footer.jsp  -->
<%@ include file="/WEB-INF/views/layout/footer.jsp"%>