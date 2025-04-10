package ex15_model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Action {
	public ActionFoward hello(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("hello", "Hellow World");
		return new ActionFoward(false,"ex15_model2/hello.jsp");
	}
	public ActionFoward loginForm(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("id", "admin");
		return new ActionFoward(false,"ex15_model2/loginForm.jsp");
	}
}
