<%@ page contentType="text/html;charset=GBK" %>
<!DOCTYPE html>
<html>
	<head>
	<%
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
	%>
       <meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<style>
			   body{
	        	background: url('<%=request.getContextPath() %>/common/images/1.jpeg');
	         }
	         p {
				font-size: 60px;
				color: white;
				text-align: center;
				position:absolute;
				top:0;
				left:0;
				right:0;
				bottom:0;
			}
		</style>
	</head>
	<body>
	<p>欢迎来到宿舍管理系统</p>
	</body>
</html>