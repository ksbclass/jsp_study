package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

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
	 	System.out.println(request.getParameter("picture"));
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
	
	/*
	 * 구글 smtp 서버를 이용하여 메일 전송하기
	 * 1. 구글 계정에 접속하여 2단계 로그인 설정
	 * 2. 앱 비밀번호 생성
	 * 3. 생성된 앱 비밀번호를 메모장을 이용하여 저장하기(cekr xnoj bdcq wfio)
	 * 4. mail.properties 파일 /WEB-INF/ 폴더에 생성하기
	 */
	@RequestMapping("mailForm")
	@MSLogin("loginAdminCheck")
	public String mailForm(HttpServletRequest request, HttpServletResponse response) {		
		// ids : 메일 전송을 위한 아이디 목록 ["test1 test2"] 
		String[] ids = request.getParameterValues("idchks");
		// list : 메일 전송을 위한 Member 객체 목록
		List<Member> list = dao.emailList(ids); 
		request.setAttribute("list", list);
		return "member/mailForm";
	}
	@RequestMapping("mailSend")
	@MSLogin("loginAdminCheck")
	public String mailSend(HttpServletRequest request, HttpServletResponse response) {	
		// 구글 아이디 @gmail.com
		String sender = request.getParameter("googleid") + "@gmail.com";
		// passwd : 앱 비밀번호
		String passwd = request.getParameter("googlepw");
		// recipient : 테스트1 <test1이메일정보>, 테스트2 <test2이메일정보>,
		String recipient = request.getParameter("recipient");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String mtype = request.getParameter("mtype");
		String result = "메일이 전송시 오류 발생했습니다.";
		Properties prop = new Properties(); // 이메일 전송을 위한 환경 설정값
		try {
			String path = request.getServletContext().getRealPath("/")+ "WEB-INF/mail.properties";
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis); // fis가 참조하는 파일의 Properties 객체의 요소로 저장
			prop.put("mail.smtp.user",sender); // 전송 이메일 주소
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Myauthenticator : 메일 전송을 위한 인증 객체
		Myauthenticator auth = new Myauthenticator(sender,passwd);
		// prop : 메일 전송을 위한 시스템 환경 설정값
		// auth : 인증 객체
		// 메일 전송을 위한 연결 객체
		Session mailSession = Session.getInstance(prop,auth);
		// msg : 메일 전송되는 데이터 객체
		MimeMessage msg = new MimeMessage(mailSession);
		List<InternetAddress> addrs = new ArrayList<InternetAddress>();
		try {
			String[] emails = recipient.split(",");
			for(String email : emails) {
				try {
					// new String(이메일주소, 인코딩 코드값)
					// email.getBytes("UTF-8" : byte[] 배열)
					// 8859_1 : 웹의 기본 인코딩 방식
					addrs.add(new InternetAddress(new String(email.getBytes("UTF-8"),"8859_1")));
				}catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			InternetAddress[] address = new InternetAddress[emails.length];
			for(int i=0; i<addrs.size(); i++) {
				address[i] = addrs.get(i);
			}
			InternetAddress from = new InternetAddress(sender);
			msg.setFrom(from); // 보내는 이메일 주소
			//Message.RecipientType.TO : 수신자
			//Message.RecipientType.CC : 참고인
			msg.setRecipients(Message.RecipientType.TO, address); 
			msg.setSubject(title); // 제목
			msg.setSentDate(new Date()); // 전송일자
			msg.setText(content); // 내용
			// multipart : 내용, 첨부파일1,첨부파일2,...
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			body.setContent(content,mtype); // 내용
			multipart.addBodyPart(body);
			msg.setText(content); // 내용
			msg.setContent(multipart);
			Transport.send(msg);// 메일 전송 부분
			result = "메일 전송이 완료 되었습니다.";
		}catch (MessagingException e) {
			e.printStackTrace();
		}
		// mailForm.jsp 에 구글 ID,구글 비밀번호 각자 계정을 value 속성값을 등록
		request.setAttribute("msg",result);
		request.setAttribute("url","list");
		return "alert";
	}
	  @RequestMapping("passwordForm")
	   @MSLogin("passwordLoginCheck")
	   public String passwordForm(HttpServletRequest request,
			   HttpServletResponse response) {
			String login = (String)request.getSession().getAttribute("login");
			if(login == null || login.trim().equals("")) {
				request.setAttribute("msg", "로그인 하세요");
				request.setAttribute("url", "loginForm");
				return "openeralert";
			}
		   return "member/passwordForm";
	   }
/*
  1. 로그인한 사용자의 비밀번호 변경만 가능.=> 로그인부분 검증
     로그아웃상태 : 로그인 하세요 메세지 출력후 
                 opener 창을 loginForm 페이지로 이동. 현재페이지 닫기
     =>passwordLoginCheck 메소드의 기능             
  2. 파라미터 저장 (pass,chgpass)
  3. 비밀번호 검증 : 현재비밀번호로 비교
     비밀번호 오류 : 비밀번호 오류 메세지 출력 후 현재페이지를 passwordForm로 이동                
  4. db에 비밀번호 수정
      boolean MemberDao.updatePass(id,변경비밀번호)
      - 수정성공 : 성공메세지 출력 후
                opener 페이지 info로 이동.현재 페이지 닫기
      - 수정실패 : 수정실패 메세지 출력 후 현재 페이지 닫기      
*/
	   @RequestMapping("password")
	   @MSLogin("passwordLoginCheck") //1
	   public String password(HttpServletRequest request,
			   HttpServletResponse response) {
		   //2
		   String pass = request.getParameter("pass");
		   String chgpass = request.getParameter("chgpass");
		   //3
		   String login = (String)request.getSession().getAttribute("login");
		   Member dbMem = dao.selectOne(login);
		   if(pass.equals(dbMem.getPass())) {//비밀번호가 맞는 경우
			   if(dao.updatePass(login, chgpass)) { //비밀번호 수정 완료
				   request.setAttribute("msg", "비밀번호가 변경되었습니다.");
				   request.setAttribute("url", "info?id="+login);
				   return "openeralert";
			   } else { //비밀번호 수정 실패
				   StringBuilder sb = new StringBuilder();
				   sb.append("alert('비밀번호 수정시 오류가 발생했습니다.');\n");
				   sb.append("self.close();");
				   request.setAttribute("script", sb.toString());
				   return "dumy"; //dumy.jsp 생성
			   }
		   } else {  //비밀번호 오류
			   request.setAttribute("msg","비밀번호가 틀렸습니다.");
			   request.setAttribute("url", "passwordForm");
			   return "alert";
		   }
	   }	   
	/*
	1. 파라미터값 (email,tel) 저장
	2. db에서 두개의 파라미터를 이용하여 id 값을 리턴해주는 함수
	   id MemberDao.idSearch(email,tel)
	3. id 값이 존재 : 화면 뒤쪽 2자를 ** 표시하여 화면에 출력하기
					아이디 전송 버튼을 클릭하면 opener 창에 id 입력란에 전달,
					화면 화면을 닫기
	   id 없음 : id가 없습니다. 현재화면 idForm 페이지로 이동
	 */
	@RequestMapping("id")
	public String id(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String id = dao.idSearch(email,tel);
		if(id==null) { // id == null : id 값 없는 경우
			request.setAttribute("msg", "해당되는 아이디가 없습니다");
			request.setAttribute("url", "idForm");
			return "alert";
		}else {
			// 뒤쪽 2자를 제외하고 jsp로 데이터 전달
			id = id.substring(0, id.length() - 2);
			request.setAttribute("id", id);
			request.setAttribute("msg","('아이디 : " + id + "**')");
			return "dumy2";
		}
	}
	/*
	1. 파라미터 저장.
  	2. db에서 id,email과 tel 을 이용하여 pass값을 리턴
       	pass = MemberDao.pwSearch(id,email,tel)
  	3. 비밀번호 검증 
     	비밀번호 찾은 경우 :화면에 앞 두자리는 **로 표시하여 화면에 출력. 닫기버튼 클릭시 
                     현재 화면 닫기
     	비밀번호 못찾은 경우: 정보에 맞는 비밀번호를 찾을 수 없습니다.  메세지 출력후
                     현재 페이지를 pwForm로 페이지 이동. 
	 */
	@RequestMapping("pass")
	public String pass(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String pw = dao.pwSearch(id, email, tel);
		if(pw==null) { // 비밀번호 검색 실패
			request.setAttribute("msg", "정보에 맞는 비밀번호를 찾을 수 없습니다.");
			request.setAttribute("url", "pwForm");
			return "alert";
		}else { // 비밀번호 검색 성공
			StringBuilder sb = new StringBuilder();
			sb.append("alert('비밀번호 : **" + pw.substring(2, pw.length()) + "');\n");
			sb.append("self.close();");
			request.setAttribute("script",sb.toString());
			return "dumy";
		}
		
	}
	/*
	 *  1. id 파라미터 
	 *  2. id를 이용하여 db에서 조회.
	 *  3. DB에서 조회 안되는 경우 : 사용가능한 아이디 입니다. 초록색으로 화면에 출력
	 *     DB에서 조회 되는 경우 : 사용 중인 아이디 입니다. 빨강색 화면에 출력
	 *  4. 닫기 버튼 클릭되면 화면 닫기
	 */
	@RequestMapping("idchk")
	public String idchk(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		Member mem = dao.selectOne(id);
		String msg = null;
		boolean able = true; //  able = true 사용가능
		if(mem == null) {
			msg ="사용가능한 아이디 입니다.";
		}else { 
			msg = "사용 중인 아이디 입니다.";
			able = false;
		}
		request.setAttribute("msg", msg);
		request.setAttribute("able", able);
		return "member/idchk";
	}
	/*
		1. request 객체로 이미지 업로드 불가 => cos.jar
		2. 이미지 업로드 폴더 : 현재폴더/picture 설정
		3. opener 화면에 이미지를 볼수 있도록 결과 전달 =>javascript
		4. 현재화면을 close 하기 					=>javascript 
	 */
	@RequestMapping("picture")
	public String picture(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getServletContext().getRealPath("")+ "picture/";
		String fname = null;
		File f = new File(path); 
		if(!f.exists()){ 
			f.mkdirs(); 
		}
		MultipartRequest multi = null;
		try {
			multi =	new MultipartRequest(request,path,10*1024*1024,"utf-8");
		}catch(IOException e) {
			e.printStackTrace();
		}
		fname = multi.getFilesystemName("picture");
		request.setAttribute("fname", fname);
		return "member/picture";
	}
	
//=======================================================================================================================================================
	public String passwordLoginCheck(HttpServletRequest request, HttpServletResponse response) {
		String login = (String) request.getSession().getAttribute("login");
		if (login == null || login.trim().equals("")) {
			request.setAttribute("msg", "로그인하세요");
			request.setAttribute("url", "loginForm");
			return "openeralert";
		}
		return null;
	}
//=======================================================================================================================================================
	// 내부 클래스 :
	// final class : 다른 클래스 의 부모 클래스가 될 수 없는 클래스
	public final class Myauthenticator extends Authenticator {
		private String id;
		private String pw;
		public Myauthenticator(String id, String pw) {
			this.id=id;
			this.pw=pw;
		}
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(id,pw);
		}
	}
//=======================================================================================================================================================
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
