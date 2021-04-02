<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h3>simpleController 에서 forward된 페이지</h3>
	요청한 결과 출력 ${ requestScope.result } <br>
	<!-- 요청한 결과 출력 <%= request.getAttribute("result") %> <br> -->
</body>
</html>