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
		<div class='menu'>�������</div>
			<div >
				<div class="box">
				<table class="table" width="90%">
					<tr>
						<td width="20%">��������<input type='text' id='name' value='' class='input'></td>
						<td width="20%">   
							 ����¥�� <select name='buildDiv' id="buildDiv" >
					     	 </select>
					    </td>
						<td width="20%">  
							<input type='button' value='��ѯ' id='query' class="button" onclick="query()"/>
							<input type='button' value='����' id='reset' class="button" onclick="reset()"/>
						</td>
					</tr>
				</table>
				</div>
			</div>	
			<div class='menu_button'>
				<input type='button' value='����' id='add' class="button" onclick="add()"/>
				<input type='button' value='�޸�' id='mod' class="button" onclick="mod()"/>
				<input type='button' value='ɾ��' id='del' class="button" onclick="del()"/>
			</div>
			<div class='table_div'>
				<table class="table" width="90%" border="1" cellspacing="1" cellpadding="4">
					<thead>
						<tr class="titletr" bgcolor="#F4F4F4">
							<th width='5%'>ѡ��</th>
							<th>������</th>
							<th>�������</th>
							<th>��������¥</th>
							<th>��ע</th>
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
			 commonUtil.showModal(url,{width:"450",height:"300px",title:'��������',confimCall:'ev_confirm',confimCallback:function(){
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
			 	alert('��ѡ��Ҫ�޸ĵļ�¼��');
			 	return false;
			 }
			 modId=id;
     		 var url='<%=request.getContextPath()%>/jsp/dorm/addDorm.jsp?id='+id;
			 commonUtil.showModal(url,{width:"450",height:"300px",title:'�޸�����',confimCall:'ev_confirm',confimCallback:function(){
			 	query();
			 }});
		}
		
		function del(){
			 var id  = modId;
			 if(id==''){
			 	alert('��ѡ��Ҫɾ���ļ�¼��');
			 	return false;
			 }
			 if(!confirm("ȷ��Ҫɾ����")){
			 	return false;
			 }
     		var url = "<%=request.getContextPath() %>/entry.do?action=dorm&method=delByAjax";
			$.post(url,{r:Math.random(),id:id},function(data){
					console.log(data)
					if(data.status == '1'){ 
						alert('�����ɹ���');
						query();		
		          	}else{
		          		alert('����ʧ�ܣ�');
		          	}
		     });
		}
		
		function init(){
	  	  //��ѯȫ������¥��Ϣ
	   	  var url = "<%=request.getContextPath() %>/entry.do?action=build&method=queryByAjax";
		  $.post(url,{r:Math.random()},function(data){
				console.log(data);
				if(data.status == '1'){ 
					var buildDiv = document.getElementById("buildDiv");
					var arr = data.list,temp,html='';
					
					html+="<option value=''>---��ѡ��---</option>"
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
					
					//֪ͨ��ҳ�����˱��
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
		          		html="<td colspan=5 style='text-align: center;'><font color=red>û�в鵽����</font></td>";
		          	}
		          	document.getElementById("tbody").innerHTML=html;
		     });	
		}
		
		var pageObj={
			el:document.getElementById("pageDiv"),
			pageNum:1,
			pageSize:10,
		}
		//������ҳ����2������Ϊ��ҳʱ�Ļص�����
		var page = new commonUtil.page(pageObj,queryData);
		function query(){
			page.query();
			
			modId='';
		} 
		
		query();
	</script>
</html>