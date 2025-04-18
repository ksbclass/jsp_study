package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.book.Book;
import model.book.BookDao;

@WebServlet(urlPatterns = { "/book/*" }, initParams = { @WebInitParam(name = "view", value = "/view/") })
public class BookController extends MskimRequestMapping  {
	private BookDao dao = new BookDao();
	
	@RequestMapping("bookwrite")
	public String bookwrite(HttpServletRequest request, HttpServletResponse response) {
		Book book = new Book();
		book.setWriter(request.getParameter("writer"));
		book.setTitle(request.getParameter("title"));
		book.setContent(request.getParameter("content"));
		if(dao.insert(book)) {
			return "redirect:bookList";
		}
		request.setAttribute("msg","게시글 등록 실패");
		request.setAttribute("url", "bookwrite");
		return "alert";
	}
	@RequestMapping("bookList")
	public String bookList(HttpServletRequest request, HttpServletResponse response) {
		
		List<Book> list = dao.list();
		request.setAttribute("list", list);
		return "book/bookList";
		
	}
}
