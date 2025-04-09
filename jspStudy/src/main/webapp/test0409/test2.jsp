<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>입력된 수까지의 합 구하기</title>
</head>
<body>
<form method="post">
  숫자:<input type="text" name="num" value="${param.num}">
   <input type="submit" value="숫자까지의 합 구하기">  
</form>
<c:forEach var="i" begin="1" end="${param.num}">
	<c:set var="sum" value="${sum+i}"/>
</c:forEach>
합계 : ${sum } <br>
<h1>if 태그를 이용하여 출력하기</h1>
<c:if test="${sum % 2 ==0}">
	<h3>${sum }은 짝수입니다.</h3>
</c:if>
<h1>choose when 태그를 이용하여 출력하기</h1>
<c:choose>
	<c:when test="${sum % 2 ==0}">
		<h3>${sum }은 짝수입니다.</h3>
	</c:when>
	<c:otherwise>
		<h3>${sum}은 홀수입니다.</h3>
	</c:otherwise>
</c:choose>
</body>
</html>