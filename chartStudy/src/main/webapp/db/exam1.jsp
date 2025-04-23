<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	최근 7일간 등록된 게시물 건수를 막대,선 그래프로 출력하기
 --%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<sql:setDataSource var="conn" driver="org.mariadb.jdbc.Driver" 
url="jdbc:mariadb://localhost:3306/gdjdb"
user="gduser" password="1234"/>
<sql:query var="rs" dataSource="${conn}">
SELECT DATE(regdate) AS regdate ,COUNT(*) as cnt 
FROM board
WHERE regdate >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
AND regdate < CURDATE()+ INTERVAL 1 DAY
group by DATE(regdate)
order by DATE(regdate) asc;
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
	[<c:forEach items = "${rs.rows}" var="m">"${m.regdate}",</c:forEach>],
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
			type : 'line',
			data : charData,
			options : {
				responsive : true,
				title : {display : true, text :"날짜별 게시판 등록 건수"},
				legend : {display : false},
				scales : {
					xAxes : [
						{display : true,
						scaleLabel : {
							display : true,
							labelString : "날짜"}
						}],
					yAxes : [{
						display : true,
						scaleLabel : {
							display : true, 
							labelString : "게시물 작성 건수",
						},
						ticks :{
							beginAtZero :true
						}
					
					}]
				}
			}
	})
	console.log(charData.datasets[0].data);
	console.log(charData.datasets[1].data);
}
</script>
</body>
</html>