<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>session을 이용한 장바구니</title>
</head>
<body>
<%-- ex02_cartAdd.jsp 구현하기
	product 파라미터의 값을 화면에 출력하기
 --%>
<form action="ex02_cartAdd.jsp" method="post">
	<select name="product">
		<option>사과</option><option>배</option><option>감</option>
		<option>자몽</option><option>귤</option><option>딸기</option>
	</select>
	<input type="submit" value="장바구니에추가">
</form>
	<a href="ex02_cartView.jsp">장바구니보기</a>
</body>
</html>