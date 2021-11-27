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
		<div class='menu'>�������Ա���� >> ����Ա��Ϣά�� </div>
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
							<div id='buildDiv'></div><span id='buildText' style='color:red;'>����¥����Ϊ��</span>
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
	  		alert('�����ɹ���');
	  	}else{
	  		alert('����ʧ�ܣ�');
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
	  	var url = "<%=request.getContextPath() %>/entry.do?action=admin&method=queryByAjaxWithNo";
			$.post(url,{r:Math.random(),no:no},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb();
				}
			});
	  }
	  
	  //ȷ��
	  function ev_confirm(callback)
	  {
	  	var no = document.getElementById('no').value;
	  	var name = document.getElementById('name').value;
	  	var build = document.getElementById("buildDiv").value;
	  	var phone = document.getElementById("phone").value;
	  	if(no==''||name==''||build==''){
	  		commonUtil.textToast('����д������ݺ��ύ��',{parent:window.parent,title:'����',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var checkVal;
	  	
	  	var pwd = document.getElementById('pwd').value||'123456';
	  	checkVal = commonUtil.checkValue(pwd,{type:'Number',length:6});
	  	if(checkVal){
	  		commonUtil.textToast("����"+checkVal,{parent:window.parent,title:'����',type:'show',isbg:true} );
	  		return false;
	  	}
	    //�ύ����̨
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
	  		alert('��ѡ������¥');
	  		return false;
	  	}
	  	
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=admin&method=addByAjax";
		$.post(url,{r:Math.random(),id:id,no:no,phone:phone,name:encodeURIComponent(name),sex:sex,build_id:build_id,pwd:pwd,type:type},function(data){
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