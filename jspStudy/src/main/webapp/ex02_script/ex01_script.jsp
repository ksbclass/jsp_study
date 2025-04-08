<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /webapp/ex01_script/ex01_script.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>스크립트의 종류</h1>
	<p>Java 코드의 영역</p>
	<ol>
	<li>스크립트릿(scriptlet) : &lt;% ... %&gt; : 자바 소스 영역.</li>
	<li>표현식(expression): &lt;%= %&gt; : 브라우저에 내용 출력</li>
	<li>선언문(declaration): &lt;%! %&gt; : 서블릿의 멤버로 변환됨.사용 안함</li>	
	</ol>
	<h1>스크립트릿 : _jspService() 메서드 소스에 설정됨.</h1>
<%
	String msg = "스크립트 예제";
	for(int i=1; i<=10; i++){
%>		
<%=i+":"+msg /*여러줄 주석*/ %><br>
<% }%>
<h1>표현식 : 브라우저에 내용 출력하기</h1>
<ul>
	<li>문장의 끝에 ; 사용 안됨</li>
	<li>값이 존재해야함 : 변수,수식,리턴값이 있는 함수 </li>
<%-- <%= System.out.println("aaa") %> --%>
	<li>주석은 반드시 /* */ 만 사용 가능. // 주석표현 사용 불가</li>
	<%=msg %> <br>
	<%=10 * 20 %> <br>
</ul>
<h1>선언문 : jsp 서블릿의 멤버로 설정</h1>
<%=this.msg %> <br>
<%=this.getMsg() %> <br>
<%!
	String msg = "선언문의 msg 변수";
	String getMsg() {
		return msg;
	}
%>
</body>
</html>