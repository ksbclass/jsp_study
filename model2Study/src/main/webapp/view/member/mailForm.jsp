<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일작성</title>
<script type="text/javascript">
function inputchk(f) {
	if(f.googleid.value == "") {
		alert("구글 아이디를 입력하세요");
		f.googleid.focus();
		return false;
	}
	if(f.googlepw.value == "") {
		alert("구글 비밀번호를 입력하세요");
		f.googlepw.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="container">
<h2 id="center">메일 보내기</h2>
<form name="form1" method="post" action="mailSend.jsp"
onsubmit="return inputchk(this)">
	<table class="table">
		<tr>
			<td>보내는 사람</td>
			<td>본인구글 ID : <input type="text" name="googleid" class="form-control">
			본인 구글 비밀번호 : <input type="password" name="googlepw"class="form-control"></td>
		</tr>
		<tr>
			<td>받는사람</td>
			<td><input type="text" name="recipient" class="form-control" value=
			"<c:forEach items="${list}" var="m">${m.name} &lt;${m.email}&gt; </c:forEach>">
		</td></tr>
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" class="form-control">
		</tr><tr><td>메시지 형식</td>
			<td><select name="mtype" class="form-control">
				<option value="text/html;charset=UTF-8">HTML
				<option value="text/plain;charset=UTF-8">TEXT
			</select>
			</td></tr>
			<tr>
				<td colspan="2">
				<textarea name="content" rows="10" cols="40" class="form-control" id="summernote"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" id="center">
				<button type="submit" class="btn btn-dark">전송</button>
				</td>
			</tr>
	</table>
</form>
</div>
</body>
</html>