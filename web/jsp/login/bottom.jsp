<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="main.user.vo.UserVO" %>
<!DOCTYPE html>
<html>
	<head>
	<%
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Expires","0");
		
		UserVO userVO = (UserVO)request.getSession().getAttribute("CurrentUser");
	 	
	 	String role = userVO.getRole_id();
		String userName = userVO.getName();
	%>
	
       <meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title></title>
		<style>
		
		body{
			background: #2C91D7;
		}
		.welcome{
			margin-top: 0;
    		color: black;
		}
		.date{
		    margin-top: 8px;
		    color: black;
		    left: 80%;
		    top: 0;
		    position: absolute;
		}
		</style>
	</head>
	<body>
		<h3 id="user" class='welcome'></h3>
		<h3 id="date" class='date'></h3>
	</body>
	<script type="text/javascript">
 // ��Date����չ���� Date ת��Ϊָ����ʽ��String
 // ��(M)����(d)��Сʱ(h)����(m)����(s)������(q) ������ 1-2 ��ռλ���� 
 // ��(y)������ 1-4 ��ռλ��������(S)ֻ���� 1 ��ռλ��(�� 1-3 λ������) 
 // ���ӣ� 
 // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
 // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
 Date.prototype.Format = function (fmt) { 
     var o = {
         "M+": this.getMonth() + 1, //�·� 
         "d+": this.getDate(), //�� 
         "h+": this.getHours(), //Сʱ 
         "m+": this.getMinutes(), //�� 
         "s+": this.getSeconds(), //�� 
         "q+": Math.floor((this.getMonth() + 3) / 3), //���� 
         "S": this.getMilliseconds() //���� 
     };
     if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
     for (var k in o)
     if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
     return fmt;
 }
 
 function getWeekDay(day){
 	    var ret="����",day=day+'';
 		var Week = ['һ','��','��','��','��','��','��'];  
	     switch (day)  
	     {   
	     	 case '0' :ret += Week[6];break;  
	         case '1' :ret += Week[day-1];break;  
	         case '2' :ret += Week[day-1];break;  
	         case '3' :ret += Week[day-1];break;  
	         case '4' :ret += Week[day-1];break;  
	         case '5' :ret += Week[day-1];break;  
	         case '6' :ret += Week[day-1];break;  
	         
	     }  
		return  ret;
 }
 
 	function init(){
		var userText="��ӭ��: <%=userName %>";
		var userType = '<%=role %>';
		if(userType=='1'){
			userText+=" ��ɫ������Ա";
		}else if(userType=='2'){
			userText+=" ��ɫ��ѧ��";
		}else if(userType=='0'){
			userText+=" ��ɫ����������Ա";
		}
		
		document.getElementById("user").innerText=userText;
		
		 var time1 = new Date().Format("yyyy-MM-dd");
		 document.getElementById("date").innerText="������:"+time1+" "+getWeekDay(new Date().getDay());
	}
	init();
		
	</script>
</html>