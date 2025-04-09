<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jstl core 태그 : forEach</title>
</head>
<body>
<h3>반복 관련 태그 : forEach</h3>
<h4>1에서 10까지 숫자 출력하기</h4>
<c:forEach var="i" begin="1" end="10">
	${i}&nbsp;&nbsp;
</c:forEach> <br>

<h4>1에서 10까지 홀수 출력하기 (step 이용)</h4>
<c:forEach var="i" begin="1" end="10" step="2">
	${i}&nbsp;&nbsp;
</c:forEach> <br>
<h4>1에서 10까지 홀수 출력하기(if 문 이용)</h4>
<c:forEach var="i" begin="1" end="10">
	<c:if test="${i%2==1}">
	${i}&nbsp;&nbsp;	
	</c:if>
</c:forEach> <br>

<h4>1에서 10까지 짝수 출력하기 (step 이용)</h4>
<c:forEach var="i" begin="2" end="10" step="2">
	${i}&nbsp;&nbsp;
</c:forEach> <br>
<h4>1에서 10까지 짝수 출력하기(if문 이용)</h4>
<c:forEach var="i" begin="1" end="10">
	<c:if test="${i%2==0}">
	${i}&nbsp;&nbsp;	
	</c:if>
</c:forEach> <br>

<h4>1에서 10까지 합 출력하기</h4>
<c:forEach var="i" begin="1" end="10">
	<c:set var="sum" value="${sum+i}"/>
</c:forEach> 
1에서 10까지의 합 : ${sum}<br>

<c:set var="sum" value="${0}"></c:set>

<h4>1에서 10까지 짝수 합 출력하기(step 이용)</h4>
<c:forEach var="i" begin="2" end="10" step="2">
	<c:set var="sum" value="${sum+i}"/>
</c:forEach> 
1에서 10까지의 짝수 합 : ${sum}<br>

<c:set var="sum" value="${0}"></c:set>

<h4>1에서 10까지 짝수 합 출력하기(if문 이용)</h4>
<c:forEach var="i" begin="1" end="10" >
	<c:if test="${i%2==0}">
	<c:set var="sum" value="${sum+i}"/>
	</c:if>
</c:forEach> 
1에서 10까지의 짝수 합 : ${sum}<br>
<c:set var="sum" value="${0}"></c:set>

<h4>1에서 10까지 홀수 합 출력하기(step 이용)</h4>
<c:forEach var="i" begin="1" end="10" step="2">
	<c:set var="sum" value="${sum+i}"/>
</c:forEach> 
1에서 10까지의 홀수 합 : ${sum}<br>

<c:set var="sum" value="${0}"></c:set>

<h4>1에서 10까지 홀수 합 출력하기(if문 이용)</h4>
<c:forEach var="i" begin="1" end="10" >
	<c:if test="${i%2==1}">
	<c:set var="sum" value="${sum+i}"/>
	</c:if>
</c:forEach> 
1에서 10까지의 홀수 합 : ${sum}<br>

<h3>forEach 태그를 이용하여 구구단 출력하기</h3>
<c:forEach var="i" begin="2" end="9">
	<h4>${i}단</h4>
	<c:forEach var="j" begin="2" end="9">
		${i } * ${j } = ${i*j }<br> 
	</c:forEach>
</c:forEach>
</body>
</html>