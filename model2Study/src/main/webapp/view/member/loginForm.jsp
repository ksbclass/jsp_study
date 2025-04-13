<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>

</head>
<body>
<form action="login" method="post" name="f" onsubmit="return input_check(this)">
<table class="table-secondary">
	<tr><th>아이디</th><td><input class="form-control" type="text" name="id"></td></tr>
	<tr><th>비밀번호</th><td><input class="form-control" type="password" name="pass"></td></tr>
	<tr ><td colspan="2"><button class="btn btn-secondary">로그인</button>
	<button class="btn btn-secondary" type="button" onclick="location.href='joinForm'">회원가입</button>
	<button class="btn btn-secondary" type="button" onclick="win_open('idForm')">아이디찾기</button>
	<button class="btn btn-secondary" type="button" onclick="win_open('pwForm')">비밀번호찾기</button>
	</td></tr>
</table>
</form>	
 <script type="text/javascript">
function input_check(e) { // e = this (form이 맞음)
	if(e.id.value.trim() == "") { // 아이디가 빈칸인경우
		alert("아이디를 입력하세요")
		e.id.focus();
		return false;
	}
	if(e.pass.value.trim() == "") { // 비밀번호가 빈칸인경우
		alert("비밀번호를 입력하세요");
		e.pass.focus();
		return false;
	}
	return true;
}
function win_open(page) { // page : 아이디 찾기 클릭시 ->idForm
	open(page,"","width=500,height=350,left=50,top=150"); // id,pass 찾기 버튼 클릭시 해당되는 jsp페이지 열기
}
 </script>
</body>
</html>