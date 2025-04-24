<%@page import="java.io.IOException"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오늘의 문제</title>
</head>
<body>
<%
String url = "https://heroesbaseball.co.kr/players/infielder/list.do";
Document doc = null;
List<String> imglist = new ArrayList<String>();
try{
	doc = Jsoup.connect(url).get();
	Elements imgs = doc.select("ul.playerList img");
	for(Element img : imgs) {
		String src = img.absUrl("src");
		System.out.println("scr :"+src);
		if(!src.isEmpty()) {
			imglist.add(src);
			}
		}
	System.out.println("imglist:"+imglist);
	}catch(IOException e) {
	e.printStackTrace();
}
pageContext.setAttribute("imglist", imglist);
%>
<c:forEach var="img" items="${imglist}">
	<img src="${img}" width="250" height="200" > <br>
</c:forEach>
</body>
</html>