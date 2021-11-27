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
</style>
	</head>
	
		<frameset id='mainFrameSet' name='mainFrameSet' rows="63,*,43" border="1" frameborder="1" FRAMESPACING="2" TOPMARGIN="0" LEFTMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0">
			<frame src="jsp/login/top.jsp"
				name="topFrame" scrolling="NO" noresize>
			<frameset cols="209,*" border="1" frameborder="1" FRAMESPACING="2">
				<frame src="jsp/login/left.jsp"
					name="leftFrame" noresize scrolling="YES">
				<frame src="jsp/login/welcome.jsp"
					name="mainFrame">
			</frameset>
			<frame src="jsp/login/bottom.jsp"
				name="bottomFrame" scrolling="NO" noresize>
		</frameset>
</html>