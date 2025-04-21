<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Random rand = new Random();
	for(int i=0;i<10; i++) {
		int num = rand.nextInt(100);
		out.print(num);
		if(i<9){
			out.print(",");
		}
	}
%>