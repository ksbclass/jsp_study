<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
1. jspStudy 프로젝트 생성
2. /webapp/ex01_directive 폴더 생성
3. ex01_directive 폴더 jsp 파일 생성
파일 이름 : ex01_pageDirective.jsp
--%>
<%-- 이파일 /jspStudy/src/main/webapp/ex01_directive/ ex01_pageDirective.jsp 임--%>
<%-- jsp 구성요소
 1.Directive(지시어) : <%@ 지시어 속성1='속성값1'... %>
	1) page directive : jsp 페이지의 특징을 설정 기능
	2) include directive : 다른 페이지를 포함 시키는 기능
	3) taglib directive : 커스텀태그(JSTL) 사용시 설정
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% // 스크립트릿 : 자바의 영역. 자바소스 코딩 영역
	Date today = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<h3>현재일시 : <%=sf.format(today) %></h3>
<h3>page directive : (지시어,지시자)</h3>
<ul>
	<li>page의 정보를 설정하는 기능</li>
	<li>형식:&lt;%@page 속성1="속성값"... %&gt; 정보를 설정하는 기능</li>
	<li>contentType : 반드시 기술해야함. MiME(text/html)타입으로 설정함</li>
	<li>import : 사용되는 클래스의 패키지이름 생략할 수 있도록 설정. 여러번 설정이 가능함</li>
	<li>isErrorPage = "true|false" => 기본값은 false.<br>
					   true 인경우 현재 페이지가 오류페이지인경우 설정
	</li>
</ul>
</body>
</html>