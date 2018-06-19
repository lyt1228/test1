<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/css/bootstrap.min.css" type="text/css" />
		<script src="${ pageContext.request.contextPath }/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${ pageContext.request.contextPath }/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<%@ include file="menu.jsp" %>

	<div class="container">
		<h1>${ msg }</h1>
		<h3><a href="${ pageContext.request.contextPath }/index.jsp">首页</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=loginUI">登录</a></h3>
		<h3><a href="${ pageContext.request.contextPath }/UserServlet?method=registUI">注册</a></h3>
	</div>


	<div style="margin-top:50px;">
			<img src="${ pageContext.request.contextPath }/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		
</body>
</html>