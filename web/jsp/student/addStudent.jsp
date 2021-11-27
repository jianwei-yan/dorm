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
		<div class='menu'>学生管理 >> 学生信息维护 </div>
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
							<select name='buildDiv' id="buildDiv" onchange="selectBuild(this.value)">
					     	 </select>
					     	 <span id='buildText' style='color:red;'>宿舍楼不能为空</span>
				      </td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">宿舍：</td>
				      <td  class='tdprimary'> 
							<select name='dormDiv' id="dormDiv" onchange=selectDorm(this)>
					     	 </select>
					     	 <span id='dormText' style='color:red;'>宿舍不能为空</span>
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
		
		  	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=queryByAjaxWithId";
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
	  						var dormEle = document.getElementById("dormDiv");
	  						var buildEle = document.getElementById("buildDiv");
	  						for(var i=0;i<buildEle.children.length;i++){
	  							var temp = buildEle.children[i];
	  							if(temp.value==data.vo.build_id){
	  								temp.selected=true;
	  								selectBuild(temp.value,dorm_cb);
	  							}else{
	  								temp.selected=false;
	  							}
	  						}
	  						
						  	//房间选择回调
							function dorm_cb(){
								for(var i=0;i<dormEle.children.length;i++){
									var temp = dormEle.children[i];
									if(temp.value==data.vo.dorm_no){
										temp.selected=true;
										default_id=temp.value;
									}else{
										temp.selected=false;
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
		
		//回到上一个选择
		function dorm_cb_last(){
			var dormEle = document.getElementById("dormDiv");
			for(var i=0;i<dormEle.children.length;i++){
				var temp = dormEle.children[i];
				if(temp.value==default_id){
					temp.selected=true;
				}else{
					temp.selected=false;
				}
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
	  	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=queryByAjaxWithNo";
			$.post(url,{r:Math.random(),no:no},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb();
				}
			});
	  }
	  
	  function findStudentCount(dorm_id,cb){
	  	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=queryStudentCountByAjax";
			$.post(url,{r:Math.random(),dorm_id:dorm_id},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb(data.count);
				}
			});
	  }
	  
	  var max_num;
	  var dorm_id;
	  var default_id='';
	  function selectDorm(obj){
	  	var index=obj.selectedIndex;
 		max_num=obj.options[index].getAttribute("max_num");
 		dorm_id=obj.value;
 		if(dorm_id){
 			findStudentCount(dorm_id,cb)
 		}
 		
 		function cb(count){
 			if(count>=max_num){
 				alert('该寝室人员已满');
 				dorm_cb_last();
 			}else{
 				default_id=dorm_id;
 			}
 		}
	  }
	  
	  //确定
	  function ev_confirm(callback)
	  {
	  	var no = document.getElementById('no').value;
	  	var name = document.getElementById('name').value;
	  	var build = document.getElementById("buildDiv").value;
	  	var dorm = document.getElementById("dormDiv").value;
	  	var phone = document.getElementById("phone").value;
	  	if(no==''||name==''||build==''||dorm==''){
	  		commonUtil.textToast('请填写相关内容后提交！',{parent:window.parent,title:'错误',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var checkVal;
	  	if(no){//修改不检查这项
	  		checkVal = commonUtil.checkValue(no,{type:'Number',length:9});
		  	if(checkVal){
		  		commonUtil.textToast("账号"+checkVal,{parent:window.parent,title:'错误',type:'show',isbg:true} );
		  		return false;
		  	}
	  	}
	  	
	  	var pwd = document.getElementById('pwd').value||'123456';
	  	checkVal = commonUtil.checkValue(pwd,{type:'Number',length:6});
	  	if(checkVal){
	  		commonUtil.textToast("密码"+checkVal,{parent:window.parent,title:'错误',type:'show',isbg:true} );
	  		return false;
	  	}
	    //提交到后台
	  	var sex = document.getElementById('sex').value;
	  	
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=addByAjax";
		$.post(url,{r:Math.random(),id:id,no:no,phone:phone,name:encodeURIComponent(name),sex:sex,build:build,dorm:dorm,pwd:pwd,type:type},function(data){
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
	  
	  //选择宿舍楼后，关联出宿舍
	  function selectBuild(value,cb){
	  	 //查询全部宿舍楼信息
	   	  var url = "<%=request.getContextPath() %>/entry.do?action=dorm&method=queryByAjax";
		  $.post(url,{r:Math.random(),build_id:value},function(data){
				console.log(data);
				if(data.status == '1'){ 
					var dormDiv = document.getElementById("dormDiv");
					var arr = data.list,temp,html='';
					
					html+="<option value=''>---请选择---</option>"
					for(var i=0;i<arr.length;i++){
						temp = arr[i];
						html+="<option value='"+temp['id']+"' max_num='"+temp['max_num']+"'>"+temp['name']+"</option>";
					}
					dormDiv.innerHTML=html;
					
					cb && cb();
				}
			})	
	  }
	</script>
</html>