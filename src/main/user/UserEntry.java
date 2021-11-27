package main.user;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.user.service.UserService;
import main.user.service.UserServiceImpl;
import main.user.vo.UserVO;
import net.sf.json.JSONObject;

public class UserEntry {

   public void execute(HttpServletRequest request,HttpServletResponse response) throws Exception
   {
      String status = "-1";
      String content="失败";
      String url="login.jsp";
      String type="";
      String method = (String)request.getParameter("method");
      
      if(method.indexOf("Ajax")!=-1){
         List list=null;
         String dataString="";
         Map retMap = null;
         if("queryByAjaxWithId".equals(method)){
            retMap = queryByAjaxWithId(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               JSONObject obj = (JSONObject)retMap.get("obj");
               dataString="{\"status\":\""+status+"\",\"vo\":"+obj+"}";
            }else{
               dataString="{\"status\":\"-1\",\"content\":\""+content+"\"}";
            }
         } else if("modPasswordByAjax".equals(method)){
            retMap = modPasswordByAjax(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               content = (String)retMap.get("content");
               dataString="{\"status\":\""+status+"\",\"content\":\""+content+"\"}";
            }else{
               dataString="{\"status\":\"-1\",\"content\":\""+content+"\"}";
            }
         }
         PrintWriter out = response.getWriter();
         out.println(dataString);
         out.close();
      
      }else{
         if("login".equals(method)){
      
         }
         else{
            status="-1";
            //url="login.jsp";
         }
         request.setAttribute("status", status);
         request.setAttribute("content", content);
         request.getRequestDispatcher(url).forward(request, response);
      }
   }
   //修改密码
   public Map modPasswordByAjax(HttpServletRequest request,HttpServletResponse response){

      HttpSession session = request.getSession();
      UserVO userVO = (UserVO)session.getAttribute("CurrentUser");
      String no="";
      if(userVO!=null){
         no=userVO.getNo();
      }
      
      String pwd = (String)request.getParameter("pwd");
      String new_pwd = (String)request.getParameter("new_pwd");
      
      UserService userService = new UserServiceImpl();
      UserVO userVO1 = userService.findByNoAndPwd(no, pwd);
      Map retMap=new HashMap();
      if(userVO1!=null){//允许改密码
         userService = new UserServiceImpl();
         userVO1.setPwd(new_pwd);
         userService.modPassword(userVO1);
         retMap.put("status", "1");
         retMap.put("content", "密码修改成功");
      }else{
          retMap.put("status", "-1");
          retMap.put("content", "原密码不正确");
      }
      return retMap;
   }
   
   //根据账号来查询
   public Map queryByAjaxWithId(HttpServletRequest request,HttpServletResponse response){
      String no = (String)request.getParameter("no");
      UserService userService = new UserServiceImpl();
      //根据账号来查询
      UserVO userVO = userService.queryByAjaxWithId(no);
      JSONObject obj=null;
      String status="-1";
      if(userVO!=null){
          obj = JSONObject.fromObject(userVO);
          status="1";
      }
      Map retMap = new HashMap();
      retMap.put("obj", obj);
      retMap.put("status", status);
      return retMap;
   }
  
}