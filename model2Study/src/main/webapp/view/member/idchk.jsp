<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복검색</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<style type="text/css">
	.able{color: green; font-size: 15px}
	.disable{color: red; font-size: 20px}
</style>
</head>
<body>
<div class="container">
	<table class="table">
		<tr>
			<td>아이디</td>
			<td>${param.id}</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="msg">${msg}</div>
			</td> 
		</tr>
		
		<tr>
			<td colspan="2">
				<button onclick="self.close()" class="btn btn-primary">닫기</button>
			</td> 
		</tr>
	</table>
</div>
<script type="text/javascript">
	if(${able}) {
		//태그.className : 태그의 class 속성을 등록
		document.querySelector("#msg").className="able";
	}else{ // 사용중인 아이디인 경우
		opener.document.f.id.value= ""; // 회원가입시 등록한 아이디 입력값을 지우기
		// 태그.setAttribute("class","disable") : 속성등록(속성이름,속성값)
		document.querySelector("#msg").setAttribute("class","disable");
	}
</script>
</body>
</html>