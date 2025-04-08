<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
	1. session 의 login 정보 제거
	2. loginForm.jsp 페이지로 이동
 --%>
<%
	//session.removeAttribute("login");
// 새로운 session 객체로 변경. 이전 session 객체에 등록된 모든 속성이 없음.
	session.invalidate();
%>
<script type="text/javascript">
	alert("로그아웃 되었습니다.");
	location.href="loginForm.jsp";
</script>