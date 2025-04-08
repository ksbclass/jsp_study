<%@page import="model.member.MemberDao"%>
<%@page import="model.member.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. id 파라미터 조회
	2. 로그인 상태 검증
		- 로그아웃 : 로그인하세요 메세지 출력. loginForm.jsp 페이지로 이동
		- 로그인 상태
			- 다른 사람의 id 정보 변경 불가(관리자는 가능).
				=> 내정보만 수정가능. main.jsp 페이지로 이동
	3. db에서 id의 해당하는 레코드를 조회하여 Member 객체로 리턴
	4. 조회된 내용이 화면에 출력하기.
--%>
<%
	String id = request.getParameter("id");
	String login = (String)session.getAttribute("login");
	if(login ==null || login.equals("")){
%>
<script type="text/javascript">
	alert("로그인하세요");
	location.href="loginForm.jsp"
</script>
<% } else if(!id.equals(login) && !login.equals("admin")) {%>
<script type="text/javascript">
	alert("내정보만 수정 가능합니다.");
	location.href="main.jsp";
</script>
<%} else { 
	Member mem = new MemberDao().selectOne(id);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정 전 화면 조회</title>
</head>
<body>
<form action="update.jsp" name="f" method="post" onsubmit="return input_check(this)">
<input type="hidden" name="picture" value="<%=mem.getPicture() %>">
<table> 
	<caption>회원정보 수정</caption>
	<tr><td rowspan="4" valign="bottom">
	<img src="picture/<%=mem.getPicture() %>" width="100" height="120" id="pic"><br>
	<font size="1"><a href="javascript:win_upload()">사진수정</a></font>
	<!-- readonly : 값 수정 불가. 파리미터 전송가능
	  disabled="disabled" :파라미터로 전송 안됨-->
	</td><th>아이디</th><td><input type="text" value="<%=mem.getId()%>" name="id" readonly ></td></tr> 
	<tr><th>비밀번호</th><td><input type="password" name="pass"></td></tr>
	<tr><th>이름</th><td><input type="text" name="name" value="<%=mem.getName()%>"></td></tr>
	<tr><th>성별</th><td>
	<input type="radio" name="gender" value="1" <%=mem.getGender()==1?"checked":"" %>>남
	<input type="radio" name="gender" value="2" <%=mem.getGender()==2?"checked":"" %>>여
	</td></tr>
	<tr><th>전화번호</th><td colspan="2"><input type="text"name="tel" value="<%=mem.getTel()%>"></td></tr>
	<tr><th>이메일</th><td colspan="2"><input type="text"name="email" value="<%=mem.getEmail()%>"></td></tr>
	<tr><td colspan="3"><button>회원수정</button>
	<% if(id.equals(login)) {%>
	<button type="button" onclick="win_passchg()">비밀번호 수정</button>
<% } %>
</td></tr>
</table>
<script type="text/javascript">
	function  inputcheck(f) {
		if(f.pass.value==""){
			alert("비밀번호를 입력하세요")
			f.pass.focus();
			return false;
		}
	}
	function win_upload() {
		let op ="width=500,height=500,left=50,top=150";
		open("pictureForm.jsp","",op);
	}
</script>
</form>
</body>
</html>
<% } %>