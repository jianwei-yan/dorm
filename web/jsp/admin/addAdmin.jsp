<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="main.user.vo.UserVO" %>
<%@page import="common.util.StringHelper"%>
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
		
		String status = StringHelper.convertStringNull((String)request.getAttribute("status"));
		String id = StringHelper.convertStringNull(request.getParameter("id"));
		String mod="";
		if(!"".equals(id)){
		   mod = "mod";
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
	<body >
		<div class='menu'>宿舍管理员管理 >> 管理员信息维护 </div>
			<div>
			<form name="form1" action="" method="post">
					<input type="hidden" name="course" id='course' value="">
	        	<table border="1" cellspacing="1" cellpadding="4" bordercolorlight=#CCCCCC bordercolordark=#FFFFFF class="table" pcolor='#FFFFE5'
			        <tr>
					  <td width="15%" noWrap align="right">账号：</td>
					  <td class='tdprimary'><input type="text" name="no" id="no"  onChange='doChange(this,"noText","账号不能为空")'>
					  						<span id='noText' style='color:red;'>账号不能为空</span></td>
			        </tr>
			        <tr>
					  <td width="15%" noWrap align="right">姓名：</td>
					  <td class='tdprimary'><input type="text" name="name" id="name"  onChange='doChange(this,"nameText","姓名不能为空")'>
					  						<span id='nameText' style='color:red;'>姓名不能为空</span></td>
			        </tr>
			         <tr>
					  <td width="15%" noWrap align="right">密码：</td>
					  <td class='tdprimary'><input type="text" name="pwd" id="pwd"  >
					  						<span style='color:red;'>如果为空，默认123456</span></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">性别：</td>
				       <td class='tdprimary'>
				       	  <select name='sex' id="sex">
				     		<option value='1'>男</option>
				     		<option value='0'>女</option>
				     	 </select>
				      </td>
			        </tr>
			         <tr>
					  <td width="15%" noWrap align="right">电话：</td>
					  <td class='tdprimary'><input type="text" name="phone" id="phone"  ></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">宿舍楼：</td>
				      <td  class='tdprimary'> 
							<div id='buildDiv'></div><span id='buildText' style='color:red;'>宿舍楼不能为空</span>
				      </td>
			        </tr>
	        	</table>
				 </form>
			</div>
	</body>
	<script src='<%=request.getContextPath() %>/common/js/jquery-1.5.1.js'></script>
	<script src='<%=request.getContextPath() %>/common/js/commonUtil.js'></script>
	<script type="text/javascript">
		var id = '<%=id %>';
		$(document).ready(function(){
			if(!id) return ;
		
		  	var url = "<%=request.getContextPath() %>/entry.do?action=admin&method=queryByAjaxWithId";
			$.post(url,{r:Math.random(),id:id},function(data){
					console.log(data);
					if(data.status == '1'){ 
						if(data&&data.vo){
	  						document.getElementById("name").value=data.vo.name;
	  						doChange(document.getElementById("name"),"nameText","姓名不能为空");
	  						document.getElementById("phone").value=data.vo.phone;
	  						document.getElementById("no").value=data.vo.no;
	  						document.getElementById("no").readOnly=true;
	  						doChange(document.getElementById("no"),"noText","账号不能为空");
	  						
	  						document.getElementById("pwd").value=data.vo.pwd;

	  						var sexEle = document.getElementById("sex");
	  						for(var i=0;i<sexEle.children.length;i++){
	  							var temp = sexEle.children[i];
	  							if(temp.value==data.vo.sex){
	  								temp.selected=true;
	  							}else{
	  								temp.selected=false;
	  							}
	  						}
	  						var bids = data.vo.build_id.split(",");
	  						
	  						var buildDiv = document.getElementById("buildDiv");
							for(var i=0;i<buildDiv.children.length;i++){
	  							var temp = buildDiv.children[i];
	  							for(var i=0;i<bids.length;i++){
		  							if(bids[i]==temp.value){
		  								temp.checked=true;
		  								break;
		  							}
		  						}
	  						}
						}
		          	}else{
		          		
		          	}
		     });
	  })
	  
	  var status = '<%=status %>';
	  if(status!=''){
	  	if(status=='1'){
	  		alert('操作成功！');
	  	}else{
	  		alert('操作失败！');
	  	}
	  }
	
	  function doChange(obj,textId,text){
	  	var textObj = document.getElementById(textId);
	  	if(obj.value==''){
	  		textObj && (textObj.innerText=text);
	  		return false;
	  	}
	  	textObj && (textObj.innerText='');
	  	
	  	if(no) return ;//新增的时候判断是否重复
	  	if(textId=='noText'){//判断账号是否存在
	  		var temp = document.getElementById('no').value;
	  		findByNo(temp,function(){
	  			textObj.innerText=obj.value+' 账号已经存在，不能重复！';
	  			obj.value='';
	  		})
	  	}
	  }
	  function findByNo(no,cb){
	  	var url = "<%=request.getContextPath() %>/entry.do?action=admin&method=queryByAjaxWithNo";
			$.post(url,{r:Math.random(),no:no},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb();
				}
			});
	  }
	  
	  //确定
	  function ev_confirm(callback)
	  {
	  	var no = document.getElementById('no').value;
	  	var name = document.getElementById('name').value;
	  	var build = document.getElementById("buildDiv").value;
	  	var phone = document.getElementById("phone").value;
	  	if(no==''||name==''||build==''){
	  		commonUtil.textToast('请填写相关内容后提交！',{parent:window.parent,title:'错误',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var checkVal;
	  	
	  	var pwd = document.getElementById('pwd').value||'123456';
	  	checkVal = commonUtil.checkValue(pwd,{type:'Number',length:6});
	  	if(checkVal){
	  		commonUtil.textToast("密码"+checkVal,{parent:window.parent,title:'错误',type:'show',isbg:true} );
	  		return false;
	  	}
	    //提交到后台
	  	var sex = document.getElementById('sex').value;
	  	
	  	var buildBox = document.getElementsByName("buildBox");
	  	var build_id = '';
	  	for(var i=0;i<buildBox.length;i++){
	  		var box = buildBox[i];
	  		if(box.checked){
	  			if(build_id==''){
		  			build_id  = box.value;
		  		}else{
		  			build_id  += ","+box.value;
		  		}
	  		}
	  	}
	  	if(build_id=='' || !build_id){
	  		alert('请选择宿舍楼');
	  		return false;
	  	}
	  	
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=admin&method=addByAjax";
		$.post(url,{r:Math.random(),id:id,no:no,phone:phone,name:encodeURIComponent(name),sex:sex,build_id:build_id,pwd:pwd,type:type},function(data){
				console.log(data)
				if(data.status == '1'){ 
					alert('操作成功！');
	          	}else{
	          		alert('操作失败！');
	          	}
	          	callback();
	     });
		  return true;  
	  }
	  
	  
	   function init(){
	  	  //查询全部宿舍楼信息
	   	  var url = "<%=request.getContextPath() %>/entry.do?action=build&method=queryByAjax";
	   	  var type = '<%=mod %>';
		  $.post(url,{r:Math.random()},function(data){
				console.log(data);
				if(data.status == '1'){ 
					var buildDiv = document.getElementById("buildDiv");
					var arr = data.list,temp,html='';
					for(var i=0;i<arr.length;i++){
						temp = arr[i];
						html+="<input type='checkbox' name='buildBox' value='"+temp['id']+"' >"+temp['name'];
					}
					buildDiv.innerHTML=html;
				}
			})	
			
	  }
	  init();
	</script>
</html>