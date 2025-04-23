<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 파이 그래프로 게시글 작성자별 건수 출력하기 --%>
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
	return Math.round(Math.random() * 255);
}
let randomColor = function(opacity) {
	return "rgba(" + randomColorFactor() + ","
	+ randomColorFactor()+ ","+ randomColorFactor() + ","
	+(opacity || ".3")+")"
}
let charData = {
		labels :[<c:forEach items = "${rs.rows}" var="m">"${m.writer}",</c:forEach>],
			datasets : [
				{
					type : 'pie',
					label : "글쓴이 별 건수",
					backgroundColor : 
					[<c:forEach items = "${rs.rows}" var="m">randomColor(1),</c:forEach>],
					data : 
					[<c:forEach items = "${rs.rows}" var="m">"${m.cnt}",</c:forEach>],
					borderWidth : 1
				}]		
}
window.onload = function() {
	let ctx = document.querySelector("#canvas");
	new Chart(ctx,{
		type : 'pie',
		data : charData,
		options : {
			responsive : true,
			title : {
				display : true,
				text :"글쓴이별 게시판 등록 건수",
				position : 'bottom'
				},
			legend : {
				display : true,
				position : 'right'
			},
		}
	})
}
</script>
</body>
</html>