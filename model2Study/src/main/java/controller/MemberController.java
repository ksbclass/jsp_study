package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.member.Member;
import model.member.MemberDao;

// http://localhost:8080/model2Study/member/joinForm
@WebServlet(urlPatterns = { "/member/*" }, initParams = { @WebInitParam(name = "view", value = "/view/") })
public class MemberController extends MskimRequestMapping {
	private MemberDao dao = new MemberDao();

	/*
	 * 1. 파라미터 정보를 Member 객체에 저장. 인코딩 필요 
	 * 2. Member 객체를 이용하여 db에 insert(member 테이블)저장
	 * 3. 가입성공 : 성공 메시지 출력후 loginForm 페이지 이동 
	 * 	가입실패 : 실패 메시지 출력후 joinForm 페이지 이동
	 * 
	 * http://localhost:8080/model2Study/member/joinForm
	 * http://localhost:8080/model2Study/member/loginForm
	 * =>@RequestMapping("joinForm") =>
	 * 필요 구현이 되지않은경우 /member / joinForm url 정보를 이용하여 / wepapp/view/memeber/
	 * joinForm.jsp 페이지가 View 로 설정되도록 구현 /member / loginForm url 정보를 이용하여 /
	 * wepapp/view/memeber/ loginForm.jsp 페이지가 View 로 설정되도록 구현
	 * forward : 서버 -> 서버 request영역을 공유함 
	 * redirect : 서버 -> 클라이언트가 다시이동 request영역이 다름
	 */

	@RequestMapping("join") // http://localhost:8080/model2Study/member/join
	public String join(HttpServletRequest request, HttpServletResponse response) {
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setGender(Integer.parseInt(request.getParameter("gender")));
		mem.setTel(request.getParameter("tel"));
		mem.setEmail(request.getParameter("email"));
		mem.setPicture(request.getParameter("picture"));
		if (dao.insert(mem)) {
			request.setAttribute("msg", mem.getName() + "님 회원 가입 되었습니다.");
			request.setAttribute("url", "loginForm");
		} else {
			request.setAttribute("msg", mem.getName() + "님 회원 가입시 오류 발생했습니다.");
			request.setAttribute("url", "joinForm");
		}
		return "alert";
	}

	@RequestMapping("login") // http://localhost:8080/model2Study/member/login
	public String login(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		Member mem = dao.selectOne(id);
		if (mem == null) {
			request.setAttribute("msg", "아이디를 확인하세요");
			request.setAttribute("url", "loginForm");
		} else {
			if (pass.equals(mem.getPass())) {
				session.setAttribute("login", id);
				request.setAttribute("msg", mem.getName() + "님이 로그인 하셨습니다.");
				request.setAttribute("url", "main");
			} else {
				request.setAttribute("msg", "비밀번호가 틀립니다");
				request.setAttribute("url", "loginForm");
			}
		}
		return "alert";
	}

	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {

		String login = (String) request.getSession().getAttribute("login");

		if (login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "alert";
		}
		return "member/main"; // forward 됨
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:loginForm"; // redirect 하도록 설정 새로운 url을 사용할때
	}

	/*
	 * 1. 로그인 상태 검증 => @MSLogin("loginIdcheck") 
	 * loginIdCheck() 함수 호출하여 검증실행 리턴 값이
	 * null 인경우 info() 호출함
	 * 로그아웃 상태 : 로그인 하세요 메시지, loginForm페이지로 이동
	 * 로그인 상태 :
	 * - 다른 id를 조회 할수 없다.단 관리자는 다른 아이디를 조회 가능함.
	 * 내정보만 조회 가능합니다. 메시지 출력. main 페이지이동
	 * 2. id 파라미터 조회
	 * 3. id 해당하는 레코드를 조회하여 Member 객체에 저장(mem)
	 */
	@RequestMapping("info")
	@MSLogin("loginIdCheck")
	public String info(HttpServletRequest request, HttpServletResponse response) {
		// 실행되는 경우는 로그인 검증이 완료된 경우
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		request.setAttribute("mem", mem); // 이름이 같아야한다.
		return "member/info";
	}

	@RequestMapping("updateForm")
	@MSLogin("loginIdCheck")
	public String updateForm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		request.setAttribute("mem", mem); 
		return "member/updateForm";
	}
/*
 	1. 모든 파라미터를 Member 객체에 저장하기
	2. 입력된 비밀번호와 db에 저장된 비밀번호 비교.
		관리자로 로그인 한 경우 관리자 비밀번호로 비교
		본인 정보 수정시 본인의 비밀번호로 비교
		불일치 : '비밀번호 오류' 메시지 출력 후 updateForm.jsp 페이지 이동
	3. Member 객체의 내용으로 db를 수정하기 : boolean MemberDao.update(Member)
		- 성공 : 회원정보 수정 완료 메시지 출력 후 info 로 페이지 이동
		- 실패 : 회원정보 수정 실패 메시지 출력 후 updateForm 페이지 이동
 */
	@RequestMapping("update")
	@MSLogin("loginIdCheck")
	public String update(HttpServletRequest request, HttpServletResponse response) {
		Member mem = new Member();
	 	mem.setId(request.getParameter("id"));
	 	mem.setPass(request.getParameter("pass"));
	 	mem.setName(request.getParameter("name"));
	 	mem.setGender(Integer.parseInt(request.getParameter("gender"))); 	
	 	mem.setTel(request.getParameter("tel"));
	 	mem.setEmail(request.getParameter("email"));
	 	mem.setPicture(request.getParameter("picture"));
	 	//2.비밀번호를 위한 db의 데이터 조회. : login 정보로 조회하기
	 	String login = (String)request.getSession().getAttribute("login");
	 	Member dbMem = dao.selectOne(login);
	 	String msg = "비밀번호가 틀립니다.";
	 	String url = "updateForm?id=" +mem.getId();
	 	if(mem.getPass().equals(dbMem.getPass())) {
	 		if(dao.update(mem)) {
	 			msg = "회원정보 수정 완료";
	 			url ="info?id="+mem.getId();
	 		}else {
	 			msg="회원정보 수정 실패";
	 		}
	 	}
	 	request.setAttribute("msg",msg);
	 	request.setAttribute("url",url);
	 	return "alert";
	}
	/*
	 	1. id 파라미터 저장
	2. 로그인 정보 검증
		- 로그아웃 상태 : 로그인하세요. loginForm로 페이지 이동
		- 본인 탈퇴 여부 검증 : 관리자를 제외하고 본인만 탈퇴가 가능
		  본인이 아닌경우 : 본인만 탈퇴가능합니다. main 로 페이지 이동 
	 */
	@RequestMapping("deleteForm")
	@MSLogin("loginIdCheck")
	public String deleteForm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		if(id.equals("admin")) {
			request.setAttribute("msg","관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url","info?id="+id);
			return "alert";
		}
		return "member/deleteForm";
	}
	/*
	 	1. 2개의 파라미터 정보를 조회하여 변수 저장
	2. 로그인 정보를 검증
	   - 로그아웃 상태 : 로그인하세요. loginForm로 페이지 이동
	   - 본인만 탈퇴가능(관리자는 제외) : 본인만 탈퇴 가능합니다. main.jsp로 페이지 이동
	   - 관리자가 탈퇴는 불가 ("admin"은 탈퇴 불가) : 관리자는 탈퇴 불가합니다. list로 페이지 이동
	3. 비밀번호 검증
	   - 로그인 정보로 비밀번호 검증. 
	   - 비밀번호 불일치 : 비밀번호 오류 메시지 출력. deleteForm로 페이지 이동
	4. db에서 id에 해당하는 회원정보 삭제하기
	   boolean MemberDao.delete(id)
	   탈퇴 성공 
	   	 - 일반 사용자 : 로그 아웃 실행. 탈퇴 메시지 출력, loginForm로 페이지 이동
	   	 - 관리자 : 탈퇴메시지 출력, list로 페이지 이동
	   탈퇴 실패
	   	 - 일반 사용자 : 탈퇴실패메시지 출력, main로 페이지 이동
	   	 - 관리자 : 탈퇴실패메시지 출력, list로 페이지 이동
	 */
	@RequestMapping("delete")
	@MSLogin("loginIdCheck")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String login = (String)request.getSession().getAttribute("login");
		if(id.equals("admin")) {
			request.setAttribute("msg","관리자는 탈퇴할 수 없습니다.");
			request.setAttribute("url","info?id="+id);
			return "alert";
		}
		Member dbMem= dao.selectOne(login);
		String msg = "비밀번호 오류 입니다.";
		String url = "deleteForm?id=" + id;
		if(pass.equals(dbMem.getPass())) {
			if(login.equals("admin")) {
				url = "list";
			}
			if(dao.delete(id)) {
				msg= id + "회원 탈퇴 성공";
				if(!login.equals("admin")) {
					request.getSession().invalidate();
					url="loginForm";
				}
			} else {
				msg= id + "회원 탈퇴 실패";
				if(!login.equals("admin")) {
					url="main";
				}
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	@RequestMapping("list")
	@MSLogin("loginAdminCheck")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		// 관리자로 로그인한 경우만 실행
		List<Member> list = dao.list(); // DB에 값을 list 형식
		request.setAttribute("list", list);
		return "member/list";
	}
	@RequestMapping("id")
	public String id(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String id = dao.idSearch(email,tel);
		if(id==null) {
			request.setAttribute("msg", "해당되는 아이디가 없습니다");
			request.setAttribute("id", id);
		}else {
			request.setAttribute("msg", "아이디 : "+id.substring(0,id.length()-2));
			request.setAttribute("id", id);
		}
		return "send"; 
	}
	@RequestMapping("mailFrom")
	@MSLogin("loginAdminCheck")
	public String mailFrom(HttpServletRequest request, HttpServletResponse response) {		
		String[] ids = request.getParameterValues("idchks");
		List<Member> list = dao.emailList(ids); 
		request.setAttribute("list", list);
		return "member/mailForm";
	}
//====================================================================================================
		public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
			String id = request.getParameter("id");
			String login = (String) request.getSession().getAttribute("login");
			if (login == null) {
				request.setAttribute("msg", "로그인하세요");
				request.setAttribute("url", "loginForm");
				return "alert";
			} else if (!login.equals("admin") && !id.equals(login)) {
				request.setAttribute("msg", "본인만 거래 가능합니다.");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null; // 정상인 경우
		}
		public String loginAdminCheck(HttpServletRequest request, HttpServletResponse response) {
			String login = (String) request.getSession().getAttribute("login");
			if (login == null) {
				request.setAttribute("msg", "로그인하세요");
				request.setAttribute("url", "loginForm");
				return "alert";
			}else if(!login.equals("admin")) {
				request.setAttribute("msg", "관리자만 가능한 업무입니다.");
				request.setAttribute("url", "main");
				return "alert";
			}
			return null; // 오류 가 없어
		}
}
