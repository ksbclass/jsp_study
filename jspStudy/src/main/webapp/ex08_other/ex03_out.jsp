<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>out 내장 객체</title>
</head>
<body>
<h3>out 객체는 response 객체의 출력버퍼에 데이터를 출력할 수 있는 출력 스트림 객체임</h3>
<%
	int sum = 0;
	for(int i=1;i<=10;i++ ) {
		sum +=i;
%>
1부터 <%=i %>까지의 부분합 : <%=sum %><br>
<% } %>
1부터 10까지의 합 : <%=sum %>
<hr>
<%
	sum = 0;
	for(int i=1;i<=10;i++) {
		sum += i ;
		out.println("1부터" + i + "까지의 부분합 : "+sum);
	}
	out.println("1부터 10까지의 합 : " +sum);
%>
</body>
</html>