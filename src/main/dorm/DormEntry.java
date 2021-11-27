package main.dorm;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.build.service.BuildService;
import main.build.service.BuildServiceImpl;
import main.build.vo.BuildVO;
import main.dorm.service.DormService;
import main.dorm.service.DormServiceImpl;
import main.dorm.vo.DormVO;
import main.user.vo.UserVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import common.util.StringHelper;

public class DormEntry
{

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
      DormService dormService = new DormServiceImpl();
      //根据账号来查询
      DormVO dormVO = dormService.queryByAjaxWithId(id);
      JSONObject obj = null;
      String status = "-1";
      if (dormVO != null)
      {
         obj = JSONObject.fromObject(dormVO);
         status = "1";
      }
      Map retMap = new HashMap();
      retMap.put("obj", obj);
      retMap.put("status", status);
      return retMap;
   }

   public Map queryByPage(HttpServletRequest request, HttpServletResponse response)
   {
      HttpSession session = request.getSession();
      UserVO userVO = (UserVO)session.getAttribute("CurrentUser");
      String type="";
      String admin_user_id="";//宿舍管理员只能管理自己权限下的学生
      if(userVO!=null){
          type=userVO.getRole_id();
          if("1".equals(type)){
             admin_user_id=userVO.getId();
          }
      }
      
      int start = 0;
      int pageSize = 10;
      int pageNum = 1;
      DormService dormService = new DormServiceImpl();
      DormVO vo = null;
      try
      {
         String build_id = StringHelper.convertStringNull((String) request.getParameter("build_id"));
         String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");
         
         vo = new DormVO();
         vo.setBuild_id(build_id);
         vo.setName(name);
         vo.setAdmin_user_id(admin_user_id);

         pageNum = Integer.parseInt((String) request.getParameter("pageNum"));
         pageSize = Integer.parseInt((String) request.getParameter("pageSize"));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      start = pageSize * (pageNum - 1);

      return dormService.queryByPage(vo, start, pageSize);
   }
   
   //根据条件查询全部
   public Map queryByAjax(HttpServletRequest request, HttpServletResponse response){
      DormService dormService = new DormServiceImpl();
      DormVO dormVO = null;
      try
      {
         String build_id = StringHelper.convertStringNull(request.getParameter("build_id"));
        /* String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");*/

         dormVO = new DormVO();
         dormVO.setBuild_id(build_id);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return dormService.queryByAjax(dormVO);
   }
   
   public Map add(HttpServletRequest request,HttpServletResponse response) throws Exception{
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      String name = StringHelper.convertStringNull(request.getParameter("name"));
      name = java.net.URLDecoder.decode(name, "utf-8");
      String remark = StringHelper.convertStringNull(request.getParameter("remark"));
      remark = java.net.URLDecoder.decode(remark, "utf-8");
      String max_num = (String)request.getParameter("max_num");
      String build_id = (String)request.getParameter("build_id");
      
      String type = (String)request.getParameter("type");
      Map retMap = null;
      
      DormVO dormVO = new DormVO();
      
      dormVO.setId(id);
      dormVO.setName(name);
      dormVO.setRemark(remark);
      dormVO.setMax_num(max_num);
      
      if("mod".equals(type)){
         DormService dormService = new DormServiceImpl();  
         dormVO.setBuild_id(build_id);
         retMap = dormService.mod(dormVO);
      }else{
         String[] bids = build_id.split(",");
         for(String bid : bids){
            DormService dormService = new DormServiceImpl();
            dormVO.setBuild_id(bid);
            dormService.add(dormVO);
         }
         retMap = new HashMap();
         retMap.put("status", "1");
      }
      return retMap;
   }
   
   public Map delByAjax(HttpServletRequest request,HttpServletResponse response){
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      DormService dormService = new DormServiceImpl();
      Map  retMap = dormService.del(id);
      
      return retMap;
   }

}