<%@page import="model.member.Member"%>
<%@page import="model.member.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. 2개의 파라미터 정보를 조회하여 변수 저장
	2. 로그인 정보를 검증
	   - 로그아웃 상태 : 로그인하세요. loginForm.jsp로 페이지 이동
	   - 본인만 탈퇴가능(관리자는 제외) : 본인만 탈퇴 가능합니다. main.jsp로 페이지 이동
	   - 관리자가 탈퇴는 불가 ("admin"은 탈퇴 불가) : 관리자는 탈퇴 불가합니다. list.jsp로 페이지 이동
	3. 비밀번호 검증
	   - 로그인 정보로 비밀번호 검증. 
	   - 비밀번호 불일치 : 비밀번호 오류 메시지 출력. deleteForm.jsp로 페이지 이동
	4. db에서 id에 해당하는 회원정보 삭제하기
	   boolean MemberDao.delete(id)
	   탈퇴 성공 
	   	 - 일반 사용자 : 로그 아웃 실행. 탈퇴 메시지 출력, loginForm.jsp로 페이지 이동
	   	 - 관리자 : 탈퇴메시지 출력, list.jsp로 페이지 이동
	   탈퇴 실패
	   	 - 일반 사용자 : 탈퇴실패메시지 출력, main.jsp로 페이지 이동
	   	 - 관리자 : 탈퇴실패메시지 출력, list.jsp로 페이지 이동
--%>
<%
	// 1. 
	request.setCharacterEncoding("utf-8");
 	String id = request.getParameter("id");
 	String pass = request.getParameter("pass");
 	String login = (String)session.getAttribute("login");
 	String msg = null;
 	String url = null;
 	if(login == null){
		msg= "로그인하세요";
		url="loginForm.jsp";
	} else if(!id.equals(login) && !login.equals("admin")) { 
		msg="본인만 탈퇴 가능합니다.";
		url="main.jsp";
	} else if(id.equals("admin")) { 
		msg="관리자는 탈퇴 불가능합니다.";
		url="list.jsp";
	} else {
		MemberDao dao = new MemberDao();
		Member dbMem = dao.selectOne(login);
		if(pass.equals(dbMem.getPass())){
		// 비밀번호 일치 =>dao에서 회원정보 삭제
			if(dao.delete(id)){
				//탈퇴 성공
				msg = id+"님 탈퇴 완료";
				if(login.equals("admin")){
					url = "list.jsp";
				}else{
					session.invalidate();
					url="loginForm.jsp";
				}
			}else{
				// 탈퇴 실패
				msg = id+"님 탈퇴 실패";
				if(login.equals("admin")){
					url = "list.jsp";
				}else{
					url="main.jsp";
				}
			}
		}else{ //비밀번호 오류
			msg="비밀번호가 틀립니다.";
			url= "deleteForm.jsp?id="+id;
		}
} 
%>
<script type="text/javascript">
    alert("<%= msg %>");
    location.href = "<%= url %>"; 
</script>