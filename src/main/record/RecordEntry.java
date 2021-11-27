package main.record;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.record.service.RecordService;
import main.record.service.RecordServiceImpl;
import main.record.vo.RecordVO;
import main.user.service.UserService;
import main.user.service.UserServiceImpl;
import main.user.vo.UserVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import common.util.StringHelper;

public class RecordEntry {

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
      RecordService recordService = new RecordServiceImpl();
      //根据账号来查询
      RecordVO recordVO = recordService.queryByAjaxWithId(id);
      JSONObject obj = null;
      String status = "-1";
      if (recordVO != null)
      {
         obj = JSONObject.fromObject(recordVO);
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
      RecordService recordService = new RecordServiceImpl();
      //根据账号来查询
      RecordVO recordVO = recordService.queryByAjaxWithNo(no);
      JSONObject obj = null;
      String status = "-1";
      if (recordVO != null)
      {
         obj = JSONObject.fromObject(recordVO);
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
      RecordService recordService = new RecordServiceImpl();
      RecordVO vo = null;
      try
      {
         String build_id = StringHelper.convertStringNull((String) request.getParameter("build_id"));
         String dorm = StringHelper.convertStringNull((String) request.getParameter("dorm"));
         String phone = StringHelper.convertStringNull((String) request.getParameter("phone"));
         String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");
         
         vo = new RecordVO();
         vo.setBuild_id(build_id);
         vo.setDorm_no(dorm);
         vo.setPhone(phone);
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

      return recordService.queryByPage(vo, start, pageSize);
   }
   
   //根据条件查询全部
   public Map queryByAjax(HttpServletRequest request, HttpServletResponse response){
      RecordService recordService = new RecordServiceImpl();
       RecordVO RecordVO = null;
      try
      {
         String build_id = StringHelper.convertStringNull(request.getParameter("build_id"));
        /* String name = StringHelper.convertStringNull(request.getParameter("name"));
         name = java.net.URLDecoder.decode(name, "utf-8");*/

         RecordVO = new RecordVO();
        // RecordVO.setBuild_id(build_id);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return recordService.queryByAjax(RecordVO);
   }
   
   public Map add(HttpServletRequest request,HttpServletResponse response) throws Exception{
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      String student_id =  StringHelper.convertStringNull((String)request.getParameter("student_id"));
      String record_date =  StringHelper.convertStringNull((String)request.getParameter("record_date"));
      String record_type =  StringHelper.convertStringNull((String)request.getParameter("record_type"));
      String remark = StringHelper.convertStringNull(request.getParameter("remark"));
      remark = java.net.URLDecoder.decode(remark, "utf-8");
      
      String type = (String)request.getParameter("type");
      Map retMap = null;
      
      
      RecordService recordService = new RecordServiceImpl(); 
      RecordVO recordVO = new RecordVO();
      recordVO.setStudent_id(student_id);
      recordVO.setRecord_date(Timestamp.valueOf(record_date+" 00:00:00"));
      recordVO.setRecord_type(record_type);
      recordVO.setRemark(remark);
      
      if("mod".equals(type)){
         recordVO.setId(id);
         retMap = recordService.mod(recordVO);
      }else{
         retMap = recordService.add(recordVO);
      }
      return retMap;
   }
   
   public Map delByAjax(HttpServletRequest request,HttpServletResponse response){
      String id = StringHelper.convertStringNull((String)request.getParameter("id"));
      RecordService recordService = new RecordServiceImpl();
      Map  retMap = recordService.del(id);
      
      return retMap;
   }


}