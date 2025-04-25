<%@page import="java.io.IOException"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String url = "https://heroesbaseball.co.kr/players/infielder/list.do";
Document doc = null;
String line = "";
List<String> imglist = new ArrayList<String>();
try{
	doc = Jsoup.connect(url).get(); // url을 접속하여 문서를 지정
	Elements imgs = doc.select("img.nail"); // img 태그들
	Elements names = doc.select("strong.name"); // 선수이름들
	for(int i=0; i<imgs.size();i++) {
		Element img = imgs.get(i);
		Element name = names.get(i);
		if(!img.toString().trim().equals("")){
			line += img.toString().replace("src=\"/files","src=\"http:heroesbaseball.co.kr/files");
			line += "<span>" +name.toString() +"</span> <br>";
		}
	}

	}catch(IOException e) {
	e.printStackTrace();
}
// pageContext.setAttribute("imglist", imglist);
%>
<%=line%>
</body>
</html>