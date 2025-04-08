<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	<sitemesh:write property="title"/> => 작성한 페이지의 title 태그의 값
	<sitemesh:write property="head"/> => 작성한 페이지의 head 태그의 값. title 태그 제외
	<sitemesh:write property='body'/> => 작성한 페이지의 body 태그의 값.
 --%>
<% String path = request.getContextPath(); // getContextPath() : 프로젝트명
										   // 절대 경로로 처리하기 위한 변수
%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property="title"/></title>
<link rel="stylesheet" href="<%= path %>/css/main.css">
<sitemesh:write property="head"/>
</head>
<body>
<table><tr><td colspan="3" style="text-align:right">
<%
	String login = (String)session.getAttribute("login");
	if(login == null) { %>
	<a href="<%=path%>/member/loginForm.jsp">로그인</a>
	<a href="<%=path%>/member/joinForm.jsp">회원가입</a>
<%} else { // 로그인된 상태%>
<%=login %>님.<a href="<%=path%>/member/logout.jsp">로그아웃</a>
<% } %>
</td></tr>
<tr><td width="15%" valign="top">
	<a href="<%=path %>/member/main.jsp">회원관리</a><br>
</td><td colspan="2" style="text-align : left; vertical-align: top">
<sitemesh:write property='body'/></td></tr>
<tr><td colspan="3">구디아카데미</td></tr>
</table>
</body>
</html>