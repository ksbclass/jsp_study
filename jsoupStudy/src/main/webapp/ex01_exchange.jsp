<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="java.io.IOException"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. jsopStudy 프로젝트 생성
	2. /webapp/ex01_exchange.jsp
	3. 3개의 jar 파일을 WEB-INF 폴더에 붙여넣기
	4. 한국 수출입 은행 사이트 방문하기
		환율 정보 url : https://www.koreaexim.go.kr/wg/HPHKWG057M01
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수출입은행 환율 정보 조회</title>
<style type="text/css">
	table{border-collapse: collapse;}
	tabel,td,th{border: 2px solid grey;}
</style>
</head>
<body>
<%
	String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
	String line = "";
	Document doc = null;
	try {
		doc = Jsoup.connect(url).get(); // url 접속후 DOM 트리 최상위 문서
		Elements e1 = doc.select("table"); // doc의 하위 태그 중 table 태그들 선택
		for(Element ele : e1){
			// ele : table 태그 한개
			String temp = ele.html();
			System.out.println(temp);
			line += temp;
		}
	}catch(IOException e) {
		e.printStackTrace();
	}
%>
<table><%=line%></table>
</body>
</html>