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
		<div class='menu'>����¥���� >> ����¥ά�� </div>
			<div>
			<form name="form1" action="" method="post">
					<input type="hidden" name="course" id='course' value="">
	        	<table border="1" cellspacing="1" cellpadding="4" bordercolorlight=#CCCCCC bordercolordark=#FFFFFF class="table" pcolor='#FFFFE5'
			        <tr>
					  <td width="15%" noWrap align="right">���ƣ�</td>
					  <td class='tdprimary'><input type="text" name="name" id="name"  onChange='doChange(this,"nameText","���Ʋ���Ϊ��")'>
					  						<span id='nameText' style='color:red;'>���Ʋ���Ϊ��</span></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">�Ƿ���Ч��</td>
				       <td class='tdprimary'>
				       	  <select name='isValid' id="isValid"  style="width:100px">
				     		<option value='Y'>��</option>
				     		<option value='N'>��</option>
				     	 </select>
				      </td>
			        </tr>
			        <tr>
					  <td width="15%" noWrap align="right">��ע��</td>
					  <td class='tdprimary'>
					  	<textarea id=remark name=remark cols=40 rows=4></textarea>
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
		
		  	var url = "<%=request.getContextPath() %>/entry.do?action=build&method=queryByAjaxWithId";
			$.post(url,{r:Math.random(),id:id},function(data){
					console.log(data);
					if(data.status == '1'){ 
						if(data&&data.vo){
	  						document.getElementById("name").value=data.vo.name;
	  						doChange(document.getElementById("name"),"nameText","���Ʋ���Ϊ��");
	  						
							document.getElementById("remark").value=data.vo.remark;
	  						var isValidEle = document.getElementById("isValid");
	  						for(var i=0;i<isValidEle.children.length;i++){
	  							var temp = isValidEle.children[i];
	  							if(temp.value==data.vo.isValid){
	  								temp.selected=true;
	  							}else{
	  								temp.selected=false;
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
	  }
	  
	  //ȷ��
	  function ev_confirm(callback)
	  {
	  	var name = document.getElementById('name').value;
	  	if(name==''){
	  		commonUtil.textToast('����д������ݺ��ύ��',{parent:window.parent,title:'����',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var remark = document.getElementById('remark').value;
	  
	    //�ύ����̨
	  	var isValid = document.getElementById('isValid').value;
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=build&method=addByAjax";
		$.post(url,{r:Math.random(),id:'<%=id%>',remark:encodeURIComponent(remark),name:encodeURIComponent(name),isValid:isValid,type:type},function(data){
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
	</script>
</html>