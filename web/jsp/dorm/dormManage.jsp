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
		   //request.getRequestDispatcher("/login.jsp").forward(request, response);
		   response.sendRedirect("/login.jsp");
		}else{
		   user = userVO.getName(); 
		}
	%>
       <meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		 <link href="<%=request.getContextPath() %>/common/css/main.css" type="text/css" rel="stylesheet">
		<style>
			*{
				margin:0;
				padding:0;
			}
		</style>
	</head>
	<body>
		<div class='menu'>宿舍管理</div>
			<div >
				<div class="box">
				<table class="table" width="90%">
					<tr>
						<td width="20%">宿舍名：<input type='text' id='name' value='' class='input'></td>
						<td width="20%">   
							 宿舍楼： <select name='buildDiv' id="buildDiv" >
					     	 </select>
					    </td>
						<td width="20%">  
							<input type='button' value='查询' id='query' class="button" onclick="query()"/>
							<input type='button' value='重置' id='reset' class="button" onclick="reset()"/>
						</td>
					</tr>
				</table>
				</div>
			</div>	
			<div class='menu_button'>
				<input type='button' value='新增' id='add' class="button" onclick="add()"/>
				<input type='button' value='修改' id='mod' class="button" onclick="mod()"/>
				<input type='button' value='删除' id='del' class="button" onclick="del()"/>
			</div>
			<div class='table_div'>
				<table class="table" width="90%" border="1" cellspacing="1" cellpadding="4">
					<thead>
						<tr class="titletr" bgcolor="#F4F4F4">
							<th width='5%'>选择</th>
							<th>宿舍名</th>
							<th>最大人数</th>
							<th>所属宿舍楼</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody id='tbody'></tbody>
				</table>
				<div id='pageDiv'></div>
			</div>
			
			<div>
			</div>
			
	</body>
	<script src='<%=request.getContextPath() %>/common/js/jquery-1.5.1.js'></script>
	<script src='<%=request.getContextPath() %>/common/js/commonUtil.js'></script>
	<script type="text/javascript">
		function reset(){
			document.getElementById('name').value='';
		}
		
		function changeColor(obj,type){
			if(type==='over'){
				obj.style.backgroundColor='lightgray';
			}else{
				obj.style.backgroundColor='';
			}
		}
		
		function changeColor(obj,type){
			if(type==='over'){
				obj.style.backgroundColor='lightgray';
			}else{
				obj.style.backgroundColor='';
			}
		}
		
		function  add(){
     		 var url='<%=request.getContextPath()%>/jsp/dorm/addDorm.jsp';
			 commonUtil.showModal(url,{width:"450",height:"300px",title:'新增宿舍',confimCall:'ev_confirm',confimCallback:function(){
			 	query();
			 }});
		}
		var modId='';
		function selectCount(id){
			modId = id;
		}
		
		function mod(){
			 var id  = modId;
			 if(id==''){
			 	alert('请选择要修改的记录！');
			 	return false;
			 }
			 modId=id;
     		 var url='<%=request.getContextPath()%>/jsp/dorm/addDorm.jsp?id='+id;
			 commonUtil.showModal(url,{width:"450",height:"300px",title:'修改宿舍',confimCall:'ev_confirm',confimCallback:function(){
			 	query();
			 }});
		}
		
		function del(){
			 var id  = modId;
			 if(id==''){
			 	alert('请选择要删除的记录！');
			 	return false;
			 }
			 if(!confirm("确定要删除？")){
			 	return false;
			 }
     		var url = "<%=request.getContextPath() %>/entry.do?action=dorm&method=delByAjax";
			$.post(url,{r:Math.random(),id:id},function(data){
					console.log(data)
					if(data.status == '1'){ 
						alert('操作成功！');
						query();		
		          	}else{
		          		alert('操作失败！');
		          	}
		     });
		}
		
		function init(){
	  	  //查询全部宿舍楼信息
	   	  var url = "<%=request.getContextPath() %>/entry.do?action=build&method=queryByAjax";
		  $.post(url,{r:Math.random()},function(data){
				console.log(data);
				if(data.status == '1'){ 
					var buildDiv = document.getElementById("buildDiv");
					var arr = data.list,temp,html='';
					
					html+="<option value=''>---请选择---</option>"
					for(var i=0;i<arr.length;i++){
						temp = arr[i];
						html+="<option value='"+temp['id']+"'>"+temp['name']+"</option>";
					}
					buildDiv.innerHTML=html;
				}
			})	
			
	  }
	  init();
	  
		
		function queryData(page){
			var html="";
			var url = "<%=request.getContextPath() %>/entry.do?action=dorm&method=queryByPageAjax";
			var name=document.getElementById("name").value;
			var build_id=document.getElementById("buildDiv").value;
			
			$.post(url,{r:Math.random(),name:encodeURIComponent(name),build_id:build_id,
							pageNum:page.pageNum,pageSize:page.pageSize},function(data){
					console.log(data)
					
					//通知分页发生了变更
					page.update(data.totalCount);
					
					if(data.status == '1'){ 
						var arr = data.list,temp,valid,disabled;
						for(var i=0;i<arr.length;i++){
							temp = arr[i];
							html+="<tr onmouseover=changeColor(this,'over') onmouseout=changeColor(this,'out')>";
							html+="<td align='center'><input type='radio' name='count' onClick=\"selectCount('"+temp['id']+"')\" ></td>";
							html+="<td align='center'>"+temp['name']+"</td>";
							html+="<td align='center'>"+temp['max_num']+"</td>";
							html+="<td align='center'>"+temp['build_name']+"</td>";
							html+="<td align='center'>"+temp['remark']+"</td>";
							html+="</tr>";
						}
		          	}else{
		          		html="<td colspan=5 style='text-align: center;'><font color=red>没有查到数据</font></td>";
		          	}
		          	document.getElementById("tbody").innerHTML=html;
		     });	
		}
		
		var pageObj={
			el:document.getElementById("pageDiv"),
			pageNum:1,
			pageSize:10,
		}
		//创建分页，第2个参数为分页时的回调函数
		var page = new commonUtil.page(pageObj,queryData);
		function query(){
			page.query();
			
			modId='';
		} 
		
		query();
	</script>
</html>