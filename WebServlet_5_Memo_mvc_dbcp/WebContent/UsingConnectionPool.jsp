<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tomcat Connection Pool 사용하기</title>
</head>
<body>
<%
	Connection conn = null;
	
	// JNDI 
	Context context = new InitialContext(); // 현재 프로젝트에서 특정 이름을 가진 녀석을 검색 
	DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/oracle"); // java:comp/env/ + 이름 (Context.xml) 파일에 정의한 name 값 (jdbc/oracle)  =>  정해진 약속 	
	
	// POOL 안에서 connection 가지고 오기
	conn = ds.getConnection();
	
	out.print("db 연결여부 : " + conn.isClosed() + "<br>"); 
	
	// POINT 반드시 집에 가실때 반환 
	conn.close();
	
	out.print("db 연결여부 : " + conn.isClosed());
%>
</body>
</html>

