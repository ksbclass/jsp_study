<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성자별 건수 그래프</title>
</head>
<body>
<sql:setDataSource var="conn" driver="org.mariadb.jdbc.Driver" 
url="jdbc:mariadb://localhost:3306/gdjdb"
user="gduser" password="1234"/>
<sql:query var="rs" dataSource="${conn}">
	select writer,count(*) cnt 
	from board
	group by writer
	order by 2 desc 
</sql:query>
<div style="width: 75%"><canvas id="canvas"></canvas></div>
<script type="text/javascript"
src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
<script type="text/javascript">
let randomColorFactor = function() {
	return Math.round(Math.random() * 255) // 0 ~ 255 사이의 임의의 값
}
// rgba (100,200,50,1) => rgba(red,green,blue,투명도)
// 투명도 : 0 ~ 1 사이의 값. 0 : 투명 , 1 : 불투명
let randomColor = function(opacity) {
	return "rgba(" + randomColorFactor() + ","
	+ randomColorFactor()+ ","+ randomColorFactor() + ","
	+(opacity || ".3")+")"
}
let charData = {
	labels : // x 축 표시
	[<c:forEach items = "${rs.rows}" var="m">"${m.writer}",</c:forEach>],
		datasets : [
			{
				type : "line",
				borderWidth : 2,
				borderColor :
				[<c:forEach items = "${rs.rows}" var="m">randomColor(1),</c:forEach>],
				label : '건수',
				fill : false,
				data : 
					[<c:forEach items = "${rs.rows}" var="m">"${m.cnt}",</c:forEach>],
			},
			{
				type : 'bar',
				label : "건수",
				backgroundColor : 
				[<c:forEach items = "${rs.rows}" var="m">randomColor(1),</c:forEach>],
				data : 
				[<c:forEach items = "${rs.rows}" var="m">"${m.cnt}",</c:forEach>],
				borderWidth : 2
			}]		
}
window.onload = function() {
	let ctx = document.querySelector("#canvas");
	new Chart(ctx,{
			type : 'bar',
			data : charData,
			options : {
				responsive : true,
				title : {display : true, text :"작성자별 게시판 등록 건수"},
				legend : {display : false},
				scales : {
					y : {beginAtZero :true},
					xAxes : [
						{display : true,
						scaleLabel : {
							display : true,
							labelString : "게시물작성자"}
						}],
					yAxes : [{
						display : true,
						scaleLabel : {
							display : true, 
							labelString : "게시물 작성 건수"
						}
					
					}]
				}
			}
	})
}
</script>
</body>
</html>