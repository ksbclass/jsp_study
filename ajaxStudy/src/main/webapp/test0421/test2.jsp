<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	int type = Integer.parseInt(request.getParameter("type"));
	int sum = 0;
	
	switch (type) {
	case 0 :
		for(int i =0; i <= num; i++) {
			sum += i;
		}
		break;
	case 1 :
		for(int i = 0; i <= num; i++) {
			if(i%2==0){
			sum += i;
			}
		}
		break;
	case 2 :
		for(int i =1; i <= num; i++) {
			if(i%2==1){				
			sum += i;
			}
		}
		break;
	}
	out.print(sum);
%>