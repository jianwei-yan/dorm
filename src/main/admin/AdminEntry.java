package main.admin;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.admin.vo.ManageBuildRelVO;
import main.user.service.UserService;
import main.user.service.UserServiceImpl;
import main.user.vo.UserVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import common.util.StringHelper;

public class AdminEntry {


   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      String status = "-1";
      String content = "失败";
      String url = "login.jsp";
      String type = "";
      String method = (String) request.getParameter("method");

      if (method.indexOf("Ajax") != -1)
      {
         List list = null;
         String dataString = "";
         Map retMap = null;
         if ("queryByAjaxWithId".equals(method))
         {
            retMap = queryByAjaxWithId(request, response);
            if (retMap != null)
            {
               status = (String) retMap.get("status");
               JSONObject obj = (JSONObject) retMap.get("obj");
               dataString = "{\"status\":\"" + status + "\",\"vo\":" + obj + "}";
            }
            else
            {
               dataString = "{\"status\":\"-1\",\"content\":\"" + content + "\"}";
            }
         }
         else if ("queryByAjaxWithNo".equals(method))
         {
            retMap = queryByAjaxWithNo(request, response);
            if (retMap != null)
            {
               status = (String) retMap.get("status");
               JSONObject obj = (JSONObject) retMap.get("obj");
               dataString = "{\"status\":\"" + status + "\",\"vo\":" + obj + "}";
            }
            else
            {
               dataString = "{\"status\":\"-1\",\"content\":\"" + content + "\"}";
            }
         }
         else if ("queryByPageAjax".equals(method))
         {
            retMap = queryByPage(request, response);
            if (retMap != null)
            {
               status = (String) retMap.get("status");
               list = (List) retMap.get("list");
               String totalCount = (String) retMap.get("totalCount");
               JSONArray array = JSONArray.fromObject(list);
               dataString = "{\"status\":\"" + status + "\",\"totalCount\":\"" + totalCount + "\",\"list\":" + array
                     + "}";
            }
            else
            {
               dataString = "{\"status\":\"-1\",\"content\":\"" + content + "\"}";
            }
         }
         else if ("queryByAjax".equals(method))
         {
            retMap = queryByAjax(request, response);
            if (retMap != null)
            {
               status = (String) retMap.get("status");
               list = (List) retMap.get("list");
               JSONArray array = JSONArray.fromObject(list);
               dataString = "{\"status\":\"" + status + "\",\"list\":" + array + "}";
            }
            else
            {
               dataString = "{\"status\":\"-1\",\"content\":\"" + content + "\"}";
            }
         }
         else if("delByAjax".equals(method)){
            retMap = delByAjax(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               dataString="{\"status\":\""+status+"\"}";
            }else{
               dataString="{\"status\":\"-1\",\"content\":\""+content+"\"}";
            }
        
         }else if("addByAjax".equals(method)){
            retMap = add(request,response);
            if(retMap!=null){
               status = (String)retMap.get("status");
               dataString="{\"status\":\""+status+"\"}";
            }else{
               dataString="{\"status\":\"-1\",\"content\":\""+content+"\"}";
            }
         }
         
         PrintWriter out = response.getWriter();
         out.println(dataString);
         out.close();

      }
      else
      {
         if ("login".equals(method))
         {

         }
         else
         {
            status = "-1";
            //url="login.jsp";
         }
         request.setAttribute("status", status);
         request.setAttribute("content", content);
         request.getRequestDispatcher(url).forward(request, response);
      }
   }

   public Map queryByAjaxWithId(HttpServletRequest request, HttpServletResponse response)
   {
      String id = (String) request.getParameter("id");
      UserService userService = new UserServiceImpl();
      //根据账号来查询
      UserVO userVO = userService.queryAdminByAjaxWithId(id);
      JSONObject obj = null;
      String status = "-1";
      if (userVO != null)
      {
         obj = JSONObject.fromObject(userVO);
         status = "1";
      }
      Map retMap = new HashMap();
      retMap.put("obj", obj);
      retMap.put("status", status);
      return retMap;
   }

   public Map queryByAjaxWithNo(HttpServletRequest request, HttpServletResponse response)
   {
      String no = (String) request.getParameter("no");
      UserService userService = new UserServiceImpl();
      //根据账号来查询
      UserVO userVO = userService.queryByAjaxWithNo(no);
      JSONObject obj = null;
      String status = "-1";
      if (userVO != null)
      {
         obj = JSONObject.fromObject(userVO);
         status = "1";
      }
      Map retMap = new HashMap();
      retMap.put("obj", obj);
      retMap.put("status", status);
      return retMap;
   }
   
   public Map queryByPage(HttpServletRequest request, HttpServletResponse response)
   {
      int start = 0;
      int pageSize = 10;
      int pageNum = 1;
      UserService userService = new UserServiceImpl();
      UserVO vo = null;
      try
      {
         String build_id = StringHelper.convertStringNull((String) request.getParameter("build_id"));
         String sex = StringHelper.convertStringNull((String) request.getParameter("sex"));
         String phone = StringHelper.convertStringNull((String) request.getParameter("phone"));
         String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");
         
         vo = new UserVO();
         vo.setBuild_id(build_id);
         vo.setSex(sex);
         vo.setPhone(phone);
         vo.setName(name);
         vo.setRole_id("1");//宿舍管理员

         pageNum = Integer.parseInt((String) request.getParameter("pageNum"));
         pageSize = Integer.parseInt((String) request.getParameter("pageSize"));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      start = pageSize * (pageNum - 1);

      return userService.queryAdminByPage(vo, start, pageSize);
   }
   
   //根据条件查询全部
   public Map queryByAjax(HttpServletRequest request, HttpServletResponse response){
      UserService userService = new UserServiceImpl();
       UserVO userVO = null;
      try
      {
         String build_id = StringHelper.convertStringNull(request.getParameter("build_id"));
        /* String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");*/

         userVO = new UserVO();
         userVO.setBuild_id(build_id);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return userService.queryByAjax(userVO);
   }
   
   public Map add(HttpServletRequest request,HttpServletResponse response) throws Exception{
      UserVO  cUserVO = (UserVO)request.getSession().getAttribute("CurrentUser");
      
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      String name = StringHelper.convertStringNull(request.getParameter("name"));
      name = java.net.URLDecoder.decode(name, "utf-8");
      String remark = StringHelper.convertStringNull(request.getParameter("remark"));
      remark = java.net.URLDecoder.decode(remark, "utf-8");
      String no =  StringHelper.convertStringNull((String)request.getParameter("no"));
//     
//      String dorm =  StringHelper.convertStringNull((String)request.getParameter("dorm"));
      
      String pwd =  StringHelper.convertStringNull((String)request.getParameter("pwd"));
      String sex =  StringHelper.convertStringNull((String)request.getParameter("sex"));
      String phone =  StringHelper.convertStringNull((String)request.getParameter("phone"));
      
      String type = (String)request.getParameter("type");
      Map retMap = null;
      
      UserService userService = new UserServiceImpl(); 
      UserVO userVO = new UserVO();
      userVO.setNo(no);
      userVO.setName(name);
      userVO.setPwd(pwd);
      //userVO.setBuild_id(build_id);
      //userVO.setDorm_no(dorm);
      userVO.setSex(sex);
      userVO.setPhone(phone);
      userVO.setRole_id("1");
      userVO.setCreator(cUserVO.getNo());
      
      if("mod".equals(type)){
         userVO.setId(id);
         retMap = userService.mod(userVO);
      }else{
         retMap = userService.add(userVO);
         if(retMap!=null){
            id=(String)retMap.get("id");
         }
      }
      
      String build_id =  StringHelper.convertStringNull((String)request.getParameter("build_id"));
      String[] bids = build_id.split(",");
      
      if("mod".equals(type)){//先根据id从中间表删除
         userService = new UserServiceImpl(); 
         userService.delRel(id);
      }
      //添加关联表信息
      ManageBuildRelVO manageBuildRelVO = new ManageBuildRelVO();
      for(String bid : bids){
         manageBuildRelVO.setBuild_id(bid);
         manageBuildRelVO.setUser_id(id);
         userService = new UserServiceImpl(); 
         userService.addRel(manageBuildRelVO);
      }
      return retMap;
   }
   //删除
   public Map delByAjax(HttpServletRequest request,HttpServletResponse response){
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      UserService userService = new UserServiceImpl();
      Map  retMap = userService.del(id);
      
      userService = new UserServiceImpl(); 
      userService.delRel(id);
      
      return retMap;
   }


}