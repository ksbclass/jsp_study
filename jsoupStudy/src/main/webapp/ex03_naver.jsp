<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>네이버 시장지표 검색</title>
</head>
<body>
<%
	String url = "https://finance.naver.com/marketindex/";
	Document doc = null;
	List<String> names = new ArrayList<>(); // 통화명저장
	List<String> values = new ArrayList<>(); // 환율 정보
	List<Double> changes = new ArrayList<>();
	try{
		doc  = Jsoup.connect(url).get(); // url을 접속하여 문서를 지정
		Elements hlist = doc.select("div.head_info"); // 환율정보,차이,상승하락
		Elements htitle = doc.select("h3.h_lst"); // 통화 명들
		for (int i=0; i<hlist.size();i++) {
			/*
			tag :<div class="head_info point_up">
			<span class="value">1,433.80</span> 
			<span class="txt_krw"><span class="blind">원</span></span>
			<span class="change">1.80</span> <span class="blind">상승</span>
			</div>
			title : <h3 class="h_lst"><span class="blind">미국 USD</span></h3>
			*/
			Element tag = hlist.get(i);
			Element title = htitle.get(i);
			
			System.out.println(tag);
			System.out.println(title);
			System.out.println("======================");
			
			//selectFirst : 첫번째 요소리턴
			String name = title.selectFirst("span.blind").html();
			// out : jsp의 내장 객체
			out.print(name + ":");
			names.add(name);
			String value = tag.selectFirst("span.value").html();
			out.print(value + "&nbsp;&nbsp;");
			values.add(value.replace(",", ""));
			String change = tag.selectFirst("span.change").html();
			out.print(change + "&nbsp;&nbsp;");			
			
			Elements blinds = tag.select("span.blind"); // 상승/ 하락 문자열
			Element blind = blinds.get(blinds.size()-1);
			out.print(blind +"<br>");
			
			double d = 0 ;
			System.out.println(blind +"=========================");
			if(blind.toString().trim().contains("하락")) {
				d = Double.parseDouble(change.replace(",",""))* -1; // 음수
			}else {
				d = Double.parseDouble(change.replace(",","")); // 양수
			}
			changes.add(d);
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	for(int i =1 ; i<=2;i++) {
		names.remove(names.size()-1);
		changes.remove(changes.size()-1);
		values.remove(values.size()-1);
	}
	pageContext.setAttribute("names",names);
	pageContext.setAttribute("values",values);
	pageContext.setAttribute("changes",changes);
	
%>
<%-- 데이터를 이용하여 change 값으로 선그래프 구현하기. 단 국제금,국내금 부분 제외 --%>
<canvas id="canvas1" width="300" height="100"></canvas>
<%-- 데이터를 이용하여 value  값으로 막대그래프 구현하기. 단 국제금,국내금 부분 제외 --%>
<canvas id="canvas2" width="300" height="100"></canvas>
<script type="text/javascript"
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script type="text/javascript"
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script type="text/javascript">
	let names =
		[<c:forEach var="code" items = "${names}">"${code}",</c:forEach>];
	let chartData1 ={
		labels : names,
		datasets : [
			{
				borderColor : "#FF00FF",
				borderWidth : 3,
				fill : false,
				data : [<c:forEach var ="chg" items ="${changes}">"${chg}",</c:forEach>]
			}

		]}
	let chartData2 ={
		labels : names,
		datasets : [
			{
				label: '환율', 
				backgroundColor: 'rgba(54, 162, 235, 0.8)', 
				borderColor : 'rgba(54, 162, 235, 1)',
				borderWidth : 1,
				data : [<c:forEach var ="val" items ="${values}">"${val}",</c:forEach>]
			}

		]}
	window.onload = function() {
	const ctx1 =  document.getElementById("canvas1");
	new Chart(ctx1,{
		type : 'line',
		data : chartData1,
		options : {
			responsive : true,
			title : {
					 display :true,
					 text : "네이버 금융 데이터(상승/하락)"
					 },
			legend : {display : false}
		}
	})
	const ctx2 =  document.getElementById("canvas2");
	new Chart(ctx2,{
		type : 'bar',
		data : chartData2,
		options : {
			responsive : true,
			title : {
					 display :true,
					 text : "네이버 금융 데이터(환율)"
					 },
			legend : {display : false}
		}
	})
	}
</script>
</body>
</html>