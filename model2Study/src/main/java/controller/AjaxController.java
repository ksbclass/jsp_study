package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;

@WebServlet(urlPatterns = {"/ajax/*"},initParams = {@WebInitParam(name = "view", value = "/view/")})
public class AjaxController extends MskimRequestMapping {
	
	@RequestMapping("select")
	public String select(HttpServletRequest request, HttpServletResponse response) {
	String name = request.getParameter("name");
	String si = request.getParameter("si");
	String gu = request.getParameter("gu");
	if(name == null) {
		name ="si";
	}
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
	String data = null;
	try {
		while((data=fr.readLine())!=null) {
			// \\s+ : 정규식표현. 공백 한개이상
			String[] arr = data.split("\\s+");
			if(arr.length >= 3) {
				 switch (name) {
                 case "si": 
                     set.add(arr[0].trim());
                     
                     break;
                 case "gu":
                     if (arr[0].equals(si)) {
                         set.add(arr[1].trim());
                     }
                     break;
                 case "dong":
                     if (arr[0].equals(si) && arr[1].equals(gu)) {
                         set.add(arr[2].trim());
                     }
                     break;
                 default:
                     break;
             }
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	request.setAttribute("list", new ArrayList<String>(set));
	request.setAttribute("len", set.size());
	return "ajax/select";	
	}
}
