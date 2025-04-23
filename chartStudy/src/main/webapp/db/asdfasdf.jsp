<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%--
	날짜별로 등록된 게시물건수를 막대 , 선그래프로출력하기
 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>날짜별 게시물건수</title>
</head>
<body>
<sql:setDataSource var="conn" driver="org.mariadb.jdbc.Driver"
	url="jdbc:mariadb://localhost:3306/gdjdb"
	user="gduser" password="1234"/>
<sql:query var="rs" dataSource="${conn}" >
select SUBSTR(regdate,1,10) 'date' ,count(*) cnt from board
group by SUBSTR(regdate,1,10)
order by SUBSTR(regdate,1,10) asc;
</sql:query>
<div style="width:75%"><canvas id="canvas"></canvas></div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script type="text/javascript">
let randomColorFactor = function(){
	return Math.round(Math.random()*255)//0~255사이의 정수
}
let randomColor = function(opacity){
//rgba(100,100,50,1) : rgba(red,green,blue,투명도)
//투명도 : 0~1사이의값,  0:투명 1:불투명
	return "rgba("+ randomColorFactor()+","
		+ randomColorFactor() + ","+randomColorFactor()+","
		+(opacity || ".3")+")";
}
let chartData = {
	labels:
[<c:forEach items="${rs.rows}" var="m">"${m.date}",</c:forEach>],
	datasets :[
		{
			type:'line',
			borderWidth : 2,
			borderColor : [<c:forEach items="${rs.rows}" var="m">randomColor(1),</c:forEach>],
			label :'건수',
			fill : false,
			data:
				[<c:forEach items="${rs.rows}" var="m">"${m.cnt}",</c:forEach>],
		},
		{
			type:'bar',
			borderWidth : 2,
			backgroundColor : [<c:forEach items="${rs.rows}" var="m">randomColor(1),</c:forEach>],
			label :'건수',
			data:
				[<c:forEach items="${rs.rows}" var="m">"${m.cnt}",</c:forEach>],
		}]
}
window.onload = function(){
	let ctx = document.querySelector("#canvas");
	new Chart(ctx,{
		type:'bar',
		data : chartData,
		options:{
		responsive : true,
			plugins:{
				legend:{
					display:false,
				},
				
				title:{
					display:true,
					text:"날짜별 게시판 등록건수"
				},
			},//plugin
			
				scales:{
					x:{
						display:true,
						title:{
						display:true,
						text:"날짜"
						},
						},
					y:{
						display:true,
						beginAtZero:true,
						title:{
						display:true,
						text:"게시물작성 건수"
						},
					}
				}
			
		}//options
	})//chart
}
</script>
</body>
</html>