<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>현재 날짜를 session 객체에 등록하기</h3>
<%
//	Date date = new Date(); // Date 객체 생성
//	session.setAttribute("today",date); // session 현재 날짜 등록
	session.setAttribute("today",new Date());
	Date date = (Date)session.getAttribute("today");
%>
등록된 날짜 : <%=date %> <br>
<a href="ex02_exam2.jsp">등록날짜 조회하기</a>
</body>
</html>