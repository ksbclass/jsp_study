<%@page import="model.member.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. 파라미터값 (email,tel) 저장
	2. db에서 두개의 파라미터를 이용하여 id 값을 리턴해주는 함수
	   id MemberDao.idSearch(email,tel)
	3. id 값이 존재 : 화면 뒤쪽 2자를 ** 표시하여 화면에 출력하기
					아이디 전송 버튼을 클릭하면 opener 창에 id 입력란에 전달,
					id.jsp 화면을 닫기
	   id 없음 : id가 없습니다. 현재화면 idForm.jsp 페이지로 이동
 --%>
<%
	//1. 파라미터 값을 저장
	String email = request.getParameter("email");
	String tel = request.getParameter("tel");
	
	//2. 아이디 찾기
	String id = new MemberDao().idSearch(email,tel);
	if(id == null) { // 조건을 만족하는 id 검색 실패
%>
<script type="text/javascript">
	alert("해당되는 아이디가 없습니다.");
	location.href ="idForm.jsp";
</script>
<% } else  {// 조건을 만족하는 id 검색 성공%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>
<table>
	<tr>
		<th>아이디</th><td><%=id.substring(0,id.length()-2)+"**" %></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="button" value="아이디 전송" 
				onclick="idsend('<%=id.substring(0,id.length()-2)%>')">
		</td>
	</tr>
</table>
<script type="text/javascript">
	function idsend(id) { // id : 실제 id에서 뒤의 2자리를 제외한 값
		//opener : loginForm.jsp
		opener.document.f.id.value = id; // loginForm.jsp 페이지의 id값 입력. 
		self.close(); // 현재 페이지를 닫기!
	}
</script>
</body>
</html>
<% }%>