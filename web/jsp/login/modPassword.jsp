<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="main.user.vo.UserVO" %>
<!DOCTYPE html>
<html>
	<head>
	<%
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		String user ="";
		UserVO userVO = (UserVO)request.getSession().getAttribute("CurrentUser");
		if(userVO==null){
		   request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
		   user = userVO.getName(); 
		}
	%>
	
       <meta http-equiv="Content-Type" content="text/html; charset=GBK">
       	 <link href="<%=request.getContextPath() %>/common/css/main.css" type="text/css" rel="stylesheet">
		<title></title>
		<style>
			*{
				margin:0;
				padding:0;
			}
		</style>
	</head>
	<body>
		<div class='menu'> >> 密码修改 </div>
			<div>
	        	<table border="1" cellspacing="1" cellpadding="4" bordercolorlight=#CCCCCC bordercolordark=#FFFFFF class="table" pcolor='#FFFFE5'
			         <tr>
					  <td width="15%" noWrap align="right">原密码：</td>
					  <td class='tdprimary'>&nbsp;<input type="text" name="pwd" id="pwd"  >
			        </tr>
			        <tr>
					  <td width="15%" noWrap align="right">新密码：</td>
					  <td class='tdprimary'>&nbsp;<input type="text" name="new_pwd" id="new_pwd"  >&nbsp;&nbsp;<font color='red'>密码现在的规则是长度小于6的数字</font>
			         </tr>
			         <tr>
					  <td width="15%" noWrap align="right">重复新密码：</td>
					  <td class='tdprimary'>&nbsp;<input type="text" name="new_pwd2" id="new_pwd2"  >
			         </tr>
	        	</table>
	        	<br>
	        		<div style="position:absolute;left:130px;">
		        		<button onclick="confirmPwd()" style="width:50px;background:skyblue;height:30px;font-size:16px;border:0;cursor:pointer;">确定</button>
		        	</div>
		</div>
	</body>
	<script src='<%=request.getContextPath() %>/common/js/jquery-1.5.1.js'></script>
	<script src='<%=request.getContextPath() %>/common/js/commonUtil.js'></script>
	<script type="text/javascript">
		
		function confirmPwd(){
			var pwd = document.getElementById("pwd").value;
			var new_pwd = document.getElementById("new_pwd").value;
			var new_pwd2 = document.getElementById("new_pwd2").value;
			if(pwd==''||new_pwd==''||new_pwd2==''){
				alert('密码不能为空！');
				return ;
			}
			
		  	var checkVal = commonUtil.checkValue(new_pwd,{type:'Number',length:6});
		  	if(checkVal){
		  		alert('新密码'+checkVal);
		  		return false;
		  	}
		  	  	
			if(new_pwd==pwd){
				alert('新密码和原密码输入一样！');
				return false;
			}
			
			if(new_pwd!=new_pwd2){
				alert('新旧密码不一样！');
				return false;
			}
			
			commitPassword(pwd,new_pwd);
		}
		
		function commitPassword(pwd,new_pwd){
		  	var url = "<%=request.getContextPath() %>/entry.do?action=user&method=modPasswordByAjax";
			$.post(url,{r:Math.random(),pwd:pwd,new_pwd:new_pwd},function(data){
					console.log(data)
					if(data.status == '1'){ 
						alert('操作成功！');
						document.getElementById("pwd").value="";
						document.getElementById("new_pwd").value="";
				        document.getElementById("new_pwd2").value="";
		          	}else{
		          		alert(data.content);
		          	}
		     });
		}
	
	</script>
</html>