<%@page import="model.member.Member"%>
<%@page import="model.member.MemberDao"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. 파라미터값을 조회
	2. db에서 입력된 아이디에 해당하는 레코드를 읽어서 Member 객체에 저장
	3. db에서 조회된 내용과 입력된 내용을 비교
	   - 아이디 존재 : 없는 경우 => id 없음 메시지 출력. loginForm.jsp 페이지로 이동
	   				있는 경우 => 비밀번호 비교
	   - 비밀번호 비교
	   		불일치 => 비밀번호 오류 => loginForm.jsp 페이지로 이동
	   		일치 => session 로그인 정보 등록. main.jsp 페이지로 이동 
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<%
	// 1 파라미터 조회
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	// 2. db에서 등록된 정보 조회
	Member mem = new MemberDao().selectOne(id);
	// 3. 내용 비교
	String msg =null;
	String url ="loginForm.jsp";
	if(mem == null) { // 아이디 없는 경우
		msg = "아이디를 확인하세요";
	} else { // 아이디 존재 하는 경우
		if(pass.equals(mem.getPass())) { // 비밀번호 비교 . 정상적인 로그인
			// pass : loginForm.jsp에서 입력한 비밀번호
			// mem.getPass() : db에 저장된 비밀번호
			session.setAttribute("login", id);
			msg = mem.getName() + "님이 로그인 하셨습니다.";
			url = "main.jsp";
		}else { // 비밀번호 오류
			msg = "비밀번호가 틀립니다";
		}
	}
%>
</head>
<body>
<script type="text/javascript">
	alert("<%=msg%>");
	location.href = "<%=url%>";
</script>
</body>
</html>