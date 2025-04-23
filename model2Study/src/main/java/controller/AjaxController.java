package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.board.BoardDao;

@WebServlet(urlPatterns = {"/ajax/*"},initParams = {@WebInitParam(name = "view", value = "/view/")})
public class AjaxController extends MskimRequestMapping {
	
	@RequestMapping("select")
	public String select(HttpServletRequest request, HttpServletResponse response) {
	BufferedReader fr = null;
	// path : sido.txt 파일의 절대 경로
	String path = request.getServletContext().getRealPath("/")+"file/sido.txt";
	try {
		fr = new BufferedReader(new FileReader(path));
	} catch (IOException e) {
		e.printStackTrace();
	}
	// LinkedHashSet : 중복불가. 순서가 유지.인덱스 사용불가
	Set<String> set = new LinkedHashSet<>();
	String si = request.getParameter("si"); //구군설정
	String gu = request.getParameter("gu"); //동리설정
	String data = null;
	if(si == null && gu==null) { //시도 설정
		try {
		  while((data=fr.readLine()) != null) {
			  // \\s+ : 정규식표현. 공백한개이상
			  String[] arr = data.split("\\s+");
			  if(arr.length >=3) set.add(arr[0].trim());
		  }
		} catch(IOException e) {
			e.printStackTrace();
		}
	} else if (gu == null) { //si파라미터값은 null이 아님
		si = si.trim();
		try {
			while((data=fr.readLine()) != null) {
				String[] arr = data.split("\\s+");
				if(arr.length >=3 && arr[0].equals(si) &&
						!arr[1].contains(arr[0]))
					set.add(arr[1].trim()); //구군 정보 설정
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	} else if (gu != null && si!=null) {
		si = si.trim();
		gu = gu.trim();
		try {
			while((data=fr.readLine()) != null) {
				String[] arr = data.split("\\s+");
				if(arr.length >=3 && arr[0].equals(si) &&
					arr[1].equals(gu) &&
					!arr[1].contains(arr[0]) &&
					!arr[2].contains(arr[1])) {
					if(arr.length > 3) {
						if(arr[3].contains(arr[1])) continue;
						arr[2] += " " + arr[3];
					}								
					set.add(arr[2].trim()); //동리 정보 설정
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	request.setAttribute("list", new ArrayList<String>(set));
	request.setAttribute("len", set.size());
	return "ajax/select";
	}
	@RequestMapping("graph1")
	public String graph1(HttpServletRequest request, HttpServletResponse response) {
		BoardDao dao = new BoardDao();
		List<Map<String,Object>> list = dao.boardgraph1();
		for(Map<String, Object> l : list) {
			System.out.println(l);
		}
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Map<String,Object> m :list) {
			for(Map.Entry<String, Object> me : m.entrySet()) {
				// l : {cnt=3, writer=홍길동}
				if(me.getKey().equals("cnt")) {
					json.append("{\"cnt\":"+me.getValue()+",");
				}
				if(me.getKey().equals("writer")) {
					json.append("\"writer\":\""+me.getValue()+"\"}");
				}
			}
			i++;
			if(i <list.size()) {
				json.append(",");
			}
		}
		json.append("]");
		request.setAttribute("json", json.toString().trim());
		return "ajax/graph";
	}
	/*
	@RequestMapping("graph2")
	public String graph2(HttpServletRequest request, HttpServletResponse response) {
		BoardDao dao = new BoardDao();
		List<Map<String,Object>> list = dao.boardgraph2();
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Map<String,Object> m :list) {
			for(Map.Entry<String, Object> me : m.entrySet()) {
				if(me.getKey().equals("cnt")) {
					json.append("{\"cnt\":"+me.getValue()+",");
				}
				if(me.getKey().equals("regdate")) {
					json.append("\"regdate\":\""+me.getValue()+"\"}");
				}
			}
			i++;
			if(i <list.size()) {
				json.append(",");
			}
		}
		json.append("]");
		request.setAttribute("json", json.toString().trim());
		return "ajax/graph";
	} 
	*/
	@RequestMapping("graph2")
	public String graph3(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDao dao = new BoardDao();
		List<Map<String, Object>> list = dao.boardgraph2();
		 ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			// List<Map<String, Object>>를 JSON 문자열로 변환
            String jsonStr = objectMapper.writeValueAsString(list);
            // 변환된 JSON 문자열 출력
            System.out.println(jsonStr);
            // 응답
			/* response.setContentType("application/json"); */
			/* response.setCharacterEncoding("UTF-8"); */
            request.setAttribute("json", jsonStr);
		} catch (IOException e) {
            e.printStackTrace();
        }
		
		return"ajax/graph";
	}
}
