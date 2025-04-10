<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	LocalDate now = LocalDate.now();
	int currentYear = now.getYear(); 
    request.setAttribute("currentYear", currentYear);
%>
이름 : ${param.name }<br>
나이 : ${param.age }<br>
성별 : %{param.gender == 1?"남":"여"}<br>
<!--  
<c:choose>
	<c:when test="${param.gender == '1'}">남</c:when>
	<c:when test="${param.gender == '2'}">여</c:when>
</c:choose><br>
-->
출생년도 : ${param.year } <br>
<c:set var="age" value="${currentYear- param.year}"/> <%-- param 에서 값을 정수로 바꿔준다. --%>
나이 : 만 ${age }세 <br>
</body>
</html>