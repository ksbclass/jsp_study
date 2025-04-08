<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--/ex04_request/ex01_request.jsp 
 파라미터 명 : <input type=".."name="파라미터명">
String request.getParameter(파라미터명) : 파라미터의 한개 값 리턴
String[] request.getParameterValues(파라미터명) : 파라미터의 값들 배열 리턴
Enumeration request.getParameterNames() : 파라미터의 이름들 리턴
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전송된 파라미터값 출력</title>
</head>
<body>
<%
	// 파라미터 데이터의 인코딩 설정. 처음에 실행해야 함.
	request.setCharacterEncoding("UTF-8");
	// <input type="text" name="name" value="홍길동">
	// name : 홍길동 => 입력된값
	String name = request.getParameter("name");
	String age = request.getParameter("age");
	// gender : 1,2 
	String gender = request.getParameter("gender");
	// getParameter("파라미터 이름") : 한개의 값만 조회함
	String hobby = request.getParameter("hobby");
	// select 태그 : option 태그의 value 값
	String year = request.getParameter("year");
%>
이름 : <%=name %><br>
나이 : <%=age %><br>
성별 : <%=gender.equals("1")?"남":"여" %><br>
취미 : <%=hobby %><br>
출생년도 : <%=year %><br>
<hr>
<h3>모든 취미 정보 조회하기</h3>
<%
	String[] hobbies = request.getParameterValues("hobby");
	for(String h : hobbies) {
%>
<%=h %> &nbsp;&nbsp;&nbsp;&nbsp; <%-- 반복문 내부의 문장 --%>
<% }%>
<hr>
<h3>모든 파리미터 정보 조회하기</h3>
<table border="1">
<tr><th>파라미터 이름 </th><th>파라미터값</th></tr>
<%--
	Enumeration : 반복자.
 	Enumeration getParameterNames() : 파라미터의 이름 목록 리턴
 --%>
<% Enumeration e = request.getParameterNames();
   while(e.hasMoreElements()) {
	   // pname : 파라미터이름 중 한개 
	   String pname = (String)e.nextElement();
	   // values : 파라미터의 값들
	   String[] values = request.getParameterValues(pname);
	%>
<%-- while 구문 내부 문장 --%>
<tr><td><%=pname %></td>
	<td><% for(String s :values) {%>
		<%=s %> &nbsp;&nbsp;
	<% } %>
	</td>
</tr>
<%} %></table>
</body>
</html>