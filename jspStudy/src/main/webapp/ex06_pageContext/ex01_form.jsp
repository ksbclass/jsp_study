<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도시 조회</title>
</head>
<body>
<select id="city">
	<option value="seoul">서울</option> 
	<option value="busan">부산</option>
</select>
<input type="button" value="도시조회" onclick="city_select(document.querySelector('#city').value)">
<script type="text/javascript">
	function city_select(city) {
		location.href ="ex01_city.jsp?city=" +city; // city = seoul|busan
	}
</script>
</body>
</html>