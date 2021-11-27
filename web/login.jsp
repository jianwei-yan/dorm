<%@ page contentType="text/html;charset=GBK" %>
<%@page import="common.util.StringHelper"%>
<!DOCTYPE html>
<html>
    <head>
    <%
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		
		String status = StringHelper.convertStringNull((String)request.getAttribute("status"));
		String content = StringHelper.convertStringNull((String)request.getAttribute("content"));
		String type = StringHelper.convertStringNull((String)request.getAttribute("type"));
	%>
       <meta http-equiv="Content-Type" content="text/html; charset=GBK">
        <title></title>
        <style>
        body{
        	background: url('<%=request.getContextPath() %>/common/images/5.jpeg');
        }
       	#login-box{
       		width: 30%;
		    height: auto;
		    margin: 0 auto;
		    margin-top: 10%;
		    text-align: center;
		    background: steelblue;
		    padding: 20px 50px;
       	}
       	#login-box h1{
		    color: #fff;
		}
		.form{
       		line-height: 25px;
       	}
       	.input{
       		float: left;
       		width: 40%;
       		margin-left:30%;
            padding: 10px 0;
            align:center;
       	}
       	.btn{
          	width: 20%;
		    font-size: 16px;
		    letter-spacing: 3px;
       	}
       	.msg{
       		color:#d1f317fc;
       	}
        </style>
    </head>
    <body>
   		<form name="form1" action="" method="post">
    		 <div id="login-box">
			      <h1>欢迎来到宿舍管理系统</h1>
			      <div class="form">
			          <div class="item">
			              <input type="text" name='no' id="no" placeholder="账号" oninput="changeMsg()">
			          </div>
			          <div class="item">
			              <input type="password" name='pwd' id="pwd" placeholder="密码" oninput="changeMsg()">
			          </div>
			      </div>
			      <p id='msg' class='msg'>请登录</p>
			      <button class='btn' onclick="login()" id='loginButton' disabled>登录</button>
			  </div>
   		</form>
    </body>
 	<script>
 		//针对session超时的跳转，如果不是最高窗口，则跳转到最高窗口
 		function checkTop(){
 			if (window != top) 
				top.location.href = location.href; 
 		}
 	   checkTop();
		
        function login(){
        	form1.action="<%=request.getContextPath() %>/entry.do?action=login&method=login";
	  		form1.submit();
        } 
        
        var noInput = document.getElementById("no");
        var pwdInput = document.getElementById("pwd");
        var msg = document.getElementById("msg");
        var loginButton = document.getElementById("loginButton");
        function changeMsg(){
        	var no=noInput.value,pwd=pwdInput.value;
        	loginButton.disabled=true;
       		if(no=='' && pwd ==''){
       			msg.innerText="请输入账号和密码";
       		}else if(no==''){
       			msg.innerText="请输入账号";
       		}else if(pwd==''){
       			msg.innerText="请输入密码";
       		}else{
       			msg.innerText="";
       			loginButton.disabled=false;
       		}
        }
        
        function init(){
        	var status = '<%=status %>';
        	var content = '<%=content %>';
        	if(status=='-1' && content!=''){
        		msg.innerText=content;
        	}
        }
       init();
 	</script>	
		
</html>