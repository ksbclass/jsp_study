<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>applocation 객체</title>
</head>
<body>
<h3>application 객체는 웹어플리케이션 당 한개의 객체가 제공됩니다. <br>
현재 웹어플리케이션 jspStudy 프로젝트의 모든 jsp는 하나의 application 객체 공유합니다.</h3>
<table border="1" style="border-collapse: collapse;">
<tr><td>jsp버전</td>
	<td><%=application.getMajorVersion() %>.
		<%=application.getMajorVersion() %></td></tr>
<tr><td>웹컨테이너 정보(WAS 서버정보)</td>
	<td><%=application.getServerInfo() %></td></tr>
<tr><td>웹어플리케이션 경로</td>
	<td><%=application.getRealPath("/") %></td></tr>
</table>
<h3>application 객체는 application 영역을 담당하는 개체임.</h3>
<% application.setAttribute("test", "application 객체 TEST"); %>
</body>
</html>