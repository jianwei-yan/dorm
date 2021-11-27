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
body {
	background: #2C91D7;
}

p {
	font-size: 40px;
	color: white;
	text-align: center;
	margin: 3px;
}

.logout {
	position: absolute;
	top: 60%;
	left: 93%;
	margin-right: 10px;
	border: 1px solid darkseagreen;
	border-radius: 8px;
	width: 80px;
	color: white;
	text-align: center;
	cursor: pointer;
}
</style>
	</head>
	<body>
		<p>宿舍管理系统</p>
		<a class="logout"  href='<%=request.getContextPath() %>/entry.do?action=login&method=logout' target='_parent' onclick=\"window.top.opener='anyone';window.top.close();\">退出系统</a>
	</body>
	<script type="text/javascript">
	   function logout(){
	   		form1.target="parent";
        	form1.action="<%=request.getContextPath() %>/entry.do?action=login&method=logout";
	  		form1.submit();
	  		window.parent.close();
	  }
	</script>
</html>