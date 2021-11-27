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
		<div class='menu'>ѧ������ >> ѧ����Ϣά�� </div>
			<div>
			<form name="form1" action="" method="post">
					<input type="hidden" name="course" id='course' value="">
	        	<table border="1" cellspacing="1" cellpadding="4" bordercolorlight=#CCCCCC bordercolordark=#FFFFFF class="table" pcolor='#FFFFE5'
			        <tr>
					  <td width="15%" noWrap align="right">�˺ţ�</td>
					  <td class='tdprimary'><input type="text" name="no" id="no"  onChange='doChange(this,"noText","�˺Ų���Ϊ��")'>
					  						<span id='noText' style='color:red;'>�˺Ų���Ϊ��</span></td>
			        </tr>
			        <tr>
					  <td width="15%" noWrap align="right">������</td>
					  <td class='tdprimary'><input type="text" name="name" id="name"  onChange='doChange(this,"nameText","��������Ϊ��")'>
					  						<span id='nameText' style='color:red;'>��������Ϊ��</span></td>
			        </tr>
			         <tr>
					  <td width="15%" noWrap align="right">���룺</td>
					  <td class='tdprimary'><input type="text" name="pwd" id="pwd"  >
					  						<span style='color:red;'>���Ϊ�գ�Ĭ��123456</span></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">�Ա�</td>
				       <td class='tdprimary'>
				       	  <select name='sex' id="sex">
				     		<option value='1'>��</option>
				     		<option value='0'>Ů</option>
				     	 </select>
				      </td>
			        </tr>
			         <tr>
					  <td width="15%" noWrap align="right">�绰��</td>
					  <td class='tdprimary'><input type="text" name="phone" id="phone"  ></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">����¥��</td>
				      <td  class='tdprimary'> 
							<select name='buildDiv' id="buildDiv" onchange="selectBuild(this.value)">
					     	 </select>
					     	 <span id='buildText' style='color:red;'>����¥����Ϊ��</span>
				      </td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">���᣺</td>
				      <td  class='tdprimary'> 
							<select name='dormDiv' id="dormDiv" onchange=selectDorm(this)>
					     	 </select>
					     	 <span id='dormText' style='color:red;'>���᲻��Ϊ��</span>
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
	  						doChange(document.getElementById("name"),"nameText","��������Ϊ��");
	  						document.getElementById("phone").value=data.vo.phone;
	  						document.getElementById("no").value=data.vo.no;
	  						document.getElementById("no").readOnly=true;
	  						doChange(document.getElementById("no"),"noText","�˺Ų���Ϊ��");
	  						
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
	  						
						  	//����ѡ��ص�
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
	  		alert('�����ɹ���');
	  	}else{
	  		alert('����ʧ�ܣ�');
	  	}
	  }
		
		//�ص���һ��ѡ��
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
	  	
	  	if(no) return ;//������ʱ���ж��Ƿ��ظ�
	  	if(textId=='noText'){//�ж��˺��Ƿ����
	  		var temp = document.getElementById('no').value;
	  		findByNo(temp,function(){
	  			textObj.innerText=obj.value+' �˺��Ѿ����ڣ������ظ���';
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
 				alert('��������Ա����');
 				dorm_cb_last();
 			}else{
 				default_id=dorm_id;
 			}
 		}
	  }
	  
	  //ȷ��
	  function ev_confirm(callback)
	  {
	  	var no = document.getElementById('no').value;
	  	var name = document.getElementById('name').value;
	  	var build = document.getElementById("buildDiv").value;
	  	var dorm = document.getElementById("dormDiv").value;
	  	var phone = document.getElementById("phone").value;
	  	if(no==''||name==''||build==''||dorm==''){
	  		commonUtil.textToast('����д������ݺ��ύ��',{parent:window.parent,title:'����',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var checkVal;
	  	if(no){//�޸Ĳ��������
	  		checkVal = commonUtil.checkValue(no,{type:'Number',length:9});
		  	if(checkVal){
		  		commonUtil.textToast("�˺�"+checkVal,{parent:window.parent,title:'����',type:'show',isbg:true} );
		  		return false;
		  	}
	  	}
	  	
	  	var pwd = document.getElementById('pwd').value||'123456';
	  	checkVal = commonUtil.checkValue(pwd,{type:'Number',length:6});
	  	if(checkVal){
	  		commonUtil.textToast("����"+checkVal,{parent:window.parent,title:'����',type:'show',isbg:true} );
	  		return false;
	  	}
	    //�ύ����̨
	  	var sex = document.getElementById('sex').value;
	  	
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=addByAjax";
		$.post(url,{r:Math.random(),id:id,no:no,phone:phone,name:encodeURIComponent(name),sex:sex,build:build,dorm:dorm,pwd:pwd,type:type},function(data){
				console.log(data)
				if(data.status == '1'){ 
					alert('�����ɹ���');
	          	}else{
	          		alert('����ʧ�ܣ�');
	          	}
	          	callback();
	     });
		  return true;  
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
	  
	  //ѡ������¥�󣬹���������
	  function selectBuild(value,cb){
	  	 //��ѯȫ������¥��Ϣ
	   	  var url = "<%=request.getContextPath() %>/entry.do?action=dorm&method=queryByAjax";
		  $.post(url,{r:Math.random(),build_id:value},function(data){
				console.log(data);
				if(data.status == '1'){ 
					var dormDiv = document.getElementById("dormDiv");
					var arr = data.list,temp,html='';
					
					html+="<option value=''>---��ѡ��---</option>"
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