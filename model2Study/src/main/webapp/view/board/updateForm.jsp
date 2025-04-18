<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 수정 화면</title>
</head>
<body>
<form action="update" method="post" enctype="multipart/form-data" name="f">
	<input type="hidden" name="num" value="${b.num}">
	<input type="hidden" name="file2" value="${b.file1}">
	<div>
		<h2 align="center">${(boardid == "1") ?"공지사항":"자유게시판"}수정</h2>
		<table class="table">
			<tr>
				<td>글쓴이</td>
				<td>
					<input type="text" name="writer" value="${b.writer}" class="form-control">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="pass" class="form-control">
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="title" value="${b.title}" class="form-control">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea rows="15" name="content" id="content" class="form-control"> ${b.content}
					</textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td style="text-align:left">
					<c:if test="${!empty b.file1}">
						<div id="file_desc">
							${b.file1}
							<a href="javascript:file_delete()">[첨부파일 삭제]</a>
						</div>
					</c:if>
					<input type="file" name="file1">
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<a href="javascript:document.f.submit()">[게시물 수정]</a>
				</td>
			</tr>
		</table>
	</div>
</form>
<script type="text/javascript">
	function file_delete() {
		document.f.file2.value="";
		file_desc.style.display="none";
	}
</script>
</body>
</html>