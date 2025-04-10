package ex15_model2;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// http://localhost:8080/jspStudy/hello.do 요청,
// hello.do 라는 파일은 존재하지 않음
// *.do => a.do,b.do,aaaa/bbbb.do..... 요청시 Controller 서블릿이 호출
@WebServlet(urlPatterns = {"*.do"},
		initParams = {@WebInitParam(name="properties",value="url.properties")})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private Properties pr = new Properties();
       private Action action = new Action();
       private Class[] paramType = new Class[] {HttpServletRequest.class,HttpServletResponse.class,};
    public Controller() {
        super();
    }
    @Override
    public void init(ServletConfig config)throws ServletException {
    	// config : properties = url.properties 값이 저장
    	FileInputStream f = null;
    	String props = config.getInitParameter("properties");
    	// props : url.properties 저장
    	try {
    		// config.getServletContext().getRealPath("/") : 웹애풀리케이션 폴더
    		// f : url.properties 파일 읽기
			f = new FileInputStream(config.getServletContext().getRealPath("/")+"WEB-INF/"+props);
			pr.load(f); // hello.do= hello -> key = value
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Object[] paramObjs = new Object[] {request,response};
		ActionFoward forward = null;
		String command =null;
		try {
			// request.getRequestURI() : /jspStudy/hello.do
			// request.getContextPath() : /jspStudy
			// command : hello.do 저장
			command = request.getRequestURI().substring(request.getContextPath().length());
			// methodName : hello
			String methodName = pr.getProperty(command);
			// Class getClass() : Object 의 멤버 메서드. 클래스 정보를 리턴
			// action.getClass() : Action 클래스의 정보
			// getMethod(hello, 파라미터 타입) : 클래스 정보에서 method 정보
			Method method = action.getClass().getMethod(methodName, paramType);
			// invoke(호출대상 객체, 매게변수 값(인자)) : 메서드 호출 
			// forward : new ActionFoward(false,"ex15_model2/hello.jsp")
			forward = (ActionFoward)method.invoke(action, paramObjs);
		} catch (NullPointerException e) {
			forward = new ActionFoward();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		if(forward != null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getView());
			}else {
				// forward.getView() : ex15_model2/hello.jsp
				// disp : 다음호출할 페이지
				RequestDispatcher disp = request.getRequestDispatcher(forward.getView());
				disp.forward(request, response); // ex15_model2/hello.jsp 페이지를 호출
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
