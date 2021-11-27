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
			background: #2C91D7;
			margin:0;
			padding:0;
		}
		.menu{
			text-align: center;
		    font-size: 30px;
		    height: 40px;
		    background: #f9c17b;
		    top: 0;
		    margin: 0;
		    padding: 0;
		    border-radius: 8px;
		}
		.ul_list{
			margin:0;
			padding: 0;
		}
		.menu_2 {
			margin-left:1px;
			display:none;
		}
		li {
			list-style:none;
		}
		.ul_list li,.menu_1 {
			display: block;
		    text-align: center;
		    font-size: 18px;
		    background-color: #42515a;
		    color: #ffffff;
		    line-height: 35px;
		    height: 35px;
		    width: 200px;
		    margin: 3px;
		    border-radius: 6px;
		    cursor:pointer;
		}
		a:link,a:visited,a:hover,a:active {
			text-decoration:none;
		}
		</style>
	</head>
	<body>
		<div class="outer">
		  <div class='menu'>主菜单</div>
	      <ul  class="ul_list" id='ul_list_id'>
	      </ul>
	  </div>
	</body>
	<script src="<%=request.getContextPath() %>/common/js/jquery-1.5.1.js"></script>
    <script>
   	 $(document).ready(function(){
   	 		//查询权限下的菜单
   	 		var ul = document.getElementById("ul_list_id");
   	 		var url = "<%=request.getContextPath() %>/entry.do?action=login&method=queryMenuAjax";
			$.post(url,{r:Math.random()},function(data){
					console.log(data)		
					var html='',list=data.list,childs=[],menu1,menu2;
					for(var i=0,len=list.length;i<len;i++){
						menu1 = list[i];
						//现在只有一层按钮
						html+=' <li onclick="'+menu1['menuClick']+'()">'+menu1['menuName']+'</li>';
					}
					
					ul.innerHTML=html;
					
					$(".menu_1").click(function(){
	  	 			 $(this).next().show(600);
	  	 			 $(this).parent().siblings().children('ul').hide(600);
	  	 			})
   	 		});
   	 
  	 	});
	
	//宿舍管理员管理 
	function adminManage(){
		var url="<%=request.getContextPath() %>/jsp/admin/adminManage.jsp";
		window.open(url,'mainFrame');
	}
	//学生管理
	function studentManage(){
		var url="<%=request.getContextPath() %>/jsp/student/studentManage.jsp";
		window.open(url,'mainFrame');
	}
	//宿舍楼管理
	function buildManage(){
		var url="<%=request.getContextPath() %>/jsp/build/buildManage.jsp";
		window.open(url,'mainFrame');
	}
	//宿舍管理
	function dormManage(){
		var url="<%=request.getContextPath() %>/jsp/dorm/dormManage.jsp";
		window.open(url,'mainFrame');
	}
	
	//考勤管理
	function recordManage(){
		var url="<%=request.getContextPath() %>/jsp/record/recordManage.jsp";
		window.open(url,'mainFrame');
	}
	//修改密码
	function modPwd(){
		var url="<%=request.getContextPath() %>/jsp/login/modPassword.jsp";
		window.open(url,'mainFrame');
	}

	</script>
</html>