<%@page import="model.member.Member"%>
<%@page import="model.member.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. 파라미터 저장(pass,chgpass)
	2. 로그인한 사용자의 비밀번호 변경만 가능 => 로그인부분 검증
	   로그 아웃상태 : 로그인 하세요 메세지 출력후
	   				opener 창을 loginForm.jsp 페이지로 이동. 현제페이지 닫기
	3. 비밀번호 검증 : 현재비밀번호로 비교
	   비밀번호 오류 : 비밀번호 오류 메시지 출력 후 현재 페이지를 passwordForm.jsp로 이동
	4. db에 비밀번호 수정
	   boolean MemberDao.updatePass(id,변경비밀번호)
	   - 수정성공 : 성공 메시지 출력후 
	   			  (로그아웃되었습니다. 변경된 비밀번호로 다시로그인하세요)
	   			  opener 페이지 info.jsp로 이동. 현재 페이지 닫기
	   - 수정 실패 : 수정 실패 메세지 출력 후 현재 페이지 닫기
--%>
<%	
	// 1. 파라미터 저장(pass,chgpass)
	String pass = request.getParameter("pass");
	String chgpass = request.getParameter("chgpass");
	//로그인 검증
	String login = (String)session.getAttribute("login");
	String id = request.getParameter("id");
	if(login == null){ // 로그 아웃 상태
%>
<script type="text/javascript">
	alert("로그인하세요");
	opener.location.href = "loginForm.jsp";
	self.close();
</script>
<%
}else {
	MemberDao dao = new MemberDao();
	Member dbMem = dao.selectOne(login);
	if(pass.equals(dbMem.getPass())){
		if(dao.updatePass(login, chgpass)){
%>
<script type="text/javascript">
	alert("비밀번호가 변경 되었습니다");
	opener.location.href = "info.jsp?id=<%=login%>";
	self.close();
</script>
<%
			}else{
%>
<script type="text/javascript">
	alert("비밀번호변경 실패");
	self.close();
	
</script>
<%
			}
	} else{
%>
<script type="text/javascript">
	alert("비밀번호가 틀립니다.");
	location.href = "passwordForm.jsp";
</script>
<%} 	
}%>
