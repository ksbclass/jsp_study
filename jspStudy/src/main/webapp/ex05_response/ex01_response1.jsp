<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>response : 응답객체</title>
</head>
<body>
<h1>response 객체는 브라우저에 전달되는 객체라 이해하면 된다. 내부에
출력 버퍼를 가지고 있어서,브라우저에 전달되는 내용을 저장하고 있다.</h1>
<h2>또한 response 객체를 이용하여 브라우저로 하여금 다른 페이지를 요청하도록 할 수
있다. 다른 페이지를 요청하도록 하는것을 redirect 라고한다.</h2>
<h2>지금 보여 지고 있는 페이지의 내용은 하나도 보이지 않음</h2>
<% response.sendRedirect("ex02_response2.jsp"); %>
</body>
</html>