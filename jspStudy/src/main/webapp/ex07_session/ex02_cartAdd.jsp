<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니추가</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String product = request.getParameter("product");
	List<String> cart =(List<String>)session.getAttribute("cart");
	if(cart== null) {
		cart = new ArrayList<String>();
		session.setAttribute("cart", cart);
	}
	cart.add(product);
%>
<script type="text/javascript">
	alert('<%=product%>이(가) 장바구니에 추가되었습니다.');
	history.go(-1); // 앞 페이지로 이동
</script>
</body>
</html>