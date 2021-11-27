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
		 <link href="<%=request.getContextPath() %>/common/css/calendar.css" rel="stylesheet" type="text/css" />
		<style>
			*{
				margin:0;
				padding:0;
			}
		</style>
	</head>
	 <script src="<%=request.getContextPath() %>/common/js/jquery-1.5.1.js" type="text/javascript"></script>
   	 <script src="<%=request.getContextPath() %>/common/js/calendar.min.js" type="text/javascript"></script>
   	 <script src='<%=request.getContextPath() %>/common/js/commonUtil.js'></script>
	<body >
		<div class='menu'> ���ڹ��� >> ������Ϣά�� </div>
			<div>
			<form name="form1" action="" method="post">
					<input type="hidden" name="course" id='course' value="">
	        	<table border="1" cellspacing="1" cellpadding="4" bordercolorlight=#CCCCCC bordercolordark=#FFFFFF class="table" pcolor='#FFFFE5'
			        <tr>
			          <td width="15%" noWrap align="right">����¥��</td>
				      <td  class='tdprimary'> 
							<select name='buildDiv' id="buildDiv" onchange="selectBuild(this.value)">
					     	 </select>
				      </td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">���᣺</td>
				      <td  class='tdprimary'> 
							<select name='dormDiv' id="dormDiv" onchange=selectDorm(this)>
					     	 </select>
				      </td>
			        </tr>
			        <tr>
					  <td width="15%" noWrap align="right">ѧ����</td>
					  <td class='tdprimary'>
					  		 <select name='studentDiv' id="studentDiv">
					     	 </select>
					     	 <span id='dormText' style='color:red;'>ѧ������Ϊ��</span></td>
			        </tr>
			        <tr>
			          <td width="15%" noWrap align="right">�������ͣ�</td>
				       <td class='tdprimary'>
				       	  <select name='record_type' id="record_type">
				       	  	<option value='2'>ȱ��</option>
				     		<option value='1'>���</option>
				     	 </select>
				      </td>
			        </tr>
			         <tr>
			          <td width="15%" noWrap align="right">���ڣ�</td>
				       <td class='tdprimary'>
				       	 <input id="record_date" style="width:110px;"  /> <span style='color:red;'>���ڲ���Ϊ��</span></td>
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
	<script type="text/javascript">
		var id = '<%=id %>';
		$(document).ready(function(){
			if(!id) return ;
		
		  	var url = "<%=request.getContextPath() %>/entry.do?action=record&method=queryByAjaxWithId";
			$.post(url,{r:Math.random(),id:id},function(data){
					console.log(data);
					if(data.status == '1'){ 
						if(data&&data.vo){
							//���ڵĴ���
							var r_date = commonUtil.dateFormat(data.vo.record_date.time,'yyyy-mm-dd');
							document.getElementById("record_date").value=r_date;
							document.getElementById("remark").value=data.vo.remark;
	  						var record_typeEle = document.getElementById("record_type");
	  						for(var i=0;i<record_typeEle.children.length;i++){
	  							var temp = record_typeEle.children[i];
	  							if(temp.value==data.vo.record_type){
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
									}else{
										temp.selected=false;
									}
								}
								//����ѧ����Ϣ
								selectDorm(dormEle,data.vo.student_id);
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
	  	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=queryByAjaxWithNo";
			$.post(url,{r:Math.random(),no:no},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb();
				}
			});
	  }
	  //��ѯ�������µ�ѧ��
	  function findStudent(dorm_id,cb){
	  	var url = "<%=request.getContextPath() %>/entry.do?action=student&method=queryStudentsByAjax";
			$.post(url,{r:Math.random(),dorm_id:dorm_id},function(data){
			console.log(data);
				if(data.status == '1'){ 
					cb(data.list);
				}
			});
	  }
	  
	  var dorm_id;
	  var default_id='';
	  function selectDorm(obj,student_id){
	  	var index=obj.selectedIndex;
 		if(obj.value){
 			findStudent(obj.value,cb);
 		}
 		
 		function cb(arr){
			var dormDiv = document.getElementById("studentDiv");
			var temp,html='';
			html+="<option value=''>---��ѡ��---</option>"
			for(var i=0;i<arr.length;i++){
				temp = arr[i];
				console.log(student_id,temp['id'])
				if(student_id==temp['id']){
					html+="<option value='"+temp['id']+"' selected=true>"+temp['name']+"</option>";
				}else{
					html+="<option value='"+temp['id']+"'>"+temp['name']+"</option>";
				}
			
			}
			dormDiv.innerHTML=html;
 		}
	  }
	  
	  //ȷ��
	  function ev_confirm(callback)
	  {
	  	var remark = document.getElementById('remark').value;
	  	var record_type = document.getElementById("record_type").value;
	  	var student_id = document.getElementById("studentDiv").value;
	  	var record_date = document.getElementById("record_date").value;
	  	
	  	if(student_id==''||record_date==''){
	  		commonUtil.textToast('����д������ݺ��ύ��',{parent:window.parent,title:'����',type:'show',isbg:true,msgType:'suc',width:400} );
	  		return false;
	  	}
	  	
	  	var type = '<%=mod %>';
  	 	var url = "<%=request.getContextPath() %>/entry.do?action=record&method=addByAjax";
		$.post(url,{r:Math.random(),id:id,student_id:student_id,record_date:record_date,record_type:record_type,remark:remark,type:type},function(data){
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
	  
	  //���ڿؼ�
	   $(function () {
	       $("#record_date").calendar({
	             controlId: "divDate",                                       // ���������ڿؼ�ID��Ĭ��: $(this).attr("id") + "Calendar"
	             speed: 200,                                                 // ����Ԥ���ٶ�֮һ���ַ���("slow", "normal", or "fast")���ʾ����ʱ���ĺ�����ֵ(�磺1000),Ĭ�ϣ�200
	             complement: true,                                           // �Ƿ���ʾ���ڻ���հ״���ǰ���µĲ���,Ĭ�ϣ�true
	             readonly: true,                                             // Ŀ������Ƿ���Ϊֻ����Ĭ�ϣ�true
	             upperLimit: new Date("2024/01/01"),                                     // �������ޣ�Ĭ�ϣ�NaN(������)
	             lowerLimit: new Date("2011/01/01")                        // �������ޣ�Ĭ�ϣ�NaN(������)
	         });
         });
	</script>
</html>