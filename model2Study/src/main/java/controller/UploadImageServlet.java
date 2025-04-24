package controller;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 * Servlet implementation class UploadImageServlet
 */
@WebServlet("/board/uploadImage")
@MultipartConfig //업로드된 파일 처리
public class UploadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UploadImageServlet() {
        super();
    }
    private static final String UPLOAD_DIR = "uploaded_images";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("file"); //file이름의 데이터를가져옴
		String fileName = getFileName(filePart); //파일명추출
	    String originalFileName = getFileName(filePart);
	   
	    // UUID로 고유한 파일명 생성
	    String savedFileName = UUID.randomUUID().toString() + getFileExtension(originalFileName);
	   
		//file.separator : window : \
		//				   리눅스 : /
		String uploadPath = //파일업로드되는폴더
				//C:/java/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/model2Study/uploaded_images
				getServletContext().getRealPath("")+File.separator+UPLOAD_DIR;
		File uploadDir = new File(uploadPath);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		//filePath  : 이미지파일이 업로드된 절대경로
		String filePath = uploadPath+File.separator+savedFileName;
		filePart.write(filePath);	//파일업로드 @MultipartConfig 필수
		
		//request.getContextPath() : 프로젝트명
		//fileUrl 톰캣이접근할수있는 url정보
		String fileUrl = request.getContextPath()+"/"+UPLOAD_DIR+"/"+savedFileName;
		response.setContentType("text/plain");
		response.getWriter().write(fileUrl); // 클라이언트(summernote)에게 url정보 전달(출력)
	}
	
	private String getFileName(Part part) {
		//content-disposition : form-data; name="file"; filename="iz.jpg"
		System.out.println("content-disposition : "+part.getHeader("content-disposition"));
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
				//오직filename만 반환
			}
		}
		return "unkown.jpg";
	}
	// 파일 확장자 추출 메서드
	private String getFileExtension(String fileName) {
	    return fileName.substring(fileName.lastIndexOf("."));
	}
}