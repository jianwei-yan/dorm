package main.login;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.login.service.MenuService;
import main.login.service.MenuServiceImpl;
import main.user.service.UserService;
import main.user.service.UserServiceImpl;
import main.user.vo.UserVO;
import net.sf.json.JSONArray;

public class LoginEntry {

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
         if("queryMenuAjax".equals(method)){
            retMap = queryMenuAjax(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               list = (List)retMap.get("list");
               JSONArray array = JSONArray.fromObject(list);
               dataString="{\"status\":\""+status+"\",\"list\":"+array+"}";
            }else{
               dataString="{\"status\":\"-1\",\"content\":\""+content+"\"}";
            }
         }
         PrintWriter out = response.getWriter();
         out.println(dataString);
         out.close();
      
      }else{
         if("login".equals(method)){
            Map retMap = login(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               content = (String)retMap.get("content");
               
               if("1".equals(status)){
                  url="jsp/login/main.jsp";
               }
            }
         }else if("logout".equals(method)){
            logout(request,response);
            status="1";
            content="请登录";
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
   
   public void logout(HttpServletRequest request,HttpServletResponse response)
   {
      HttpSession session = request.getSession();
      //String sessionId = session.getId();
      /* 设置Session失效 */
      session.invalidate();
   }
   
   public Map login(HttpServletRequest request,HttpServletResponse response){
      // 将登录成功的日志信息写到session 
      
      String no = (String)request.getParameter("no");
      String pwd = (String)request.getParameter("pwd");
      UserService userService = new UserServiceImpl();
      UserVO userVO = userService.findByNoAndPwd(no, pwd);
      Map retMap=new HashMap();
      if(userVO!=null){
          retMap.put("status", "1");
          retMap.put("content", "登录成功");
          //放到session中
          request.getSession().setAttribute("CurrentUser", userVO);
      }else{
    	  retMap.put("status", "-1");
          retMap.put("content", "账号或者密码不正确");
      }
      return retMap;
   }
   
   public Map queryMenuAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
      HttpSession session = request.getSession();
      UserVO userVO = (UserVO)session.getAttribute("CurrentUser");
      String type="";
	  if(userVO!=null){//通过
		  type=userVO.getRole_id();
	  }
      MenuService menuService = new MenuServiceImpl();
      List list = menuService.getMenu(type);
      Map  retMap = new HashMap();
      retMap.put("list", list);
      retMap.put("status", "1");
      return retMap;
   }
  
}