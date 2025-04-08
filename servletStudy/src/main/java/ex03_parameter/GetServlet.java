package ex03_parameter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetServlet")
public class GetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public GetServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파라미터의 인코딩 방식은 UTF-8로 설정
		request.setCharacterEncoding("UTF-8");
		// name=model 인 입력 값을 조회
		String model = request.getParameter("model");
		String price = request.getParameter("price");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("모델명: " +model).append(",가격 :" +price);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
