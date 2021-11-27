package main.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.admin.AdminEntry;
import main.build.BuildEntry;
import main.dorm.DormEntry;
import main.login.LoginEntry;
import main.record.RecordEntry;
import main.student.StudentEntry;
import main.user.UserEntry;
import net.sf.json.JSONObject;

public class EntryServlet extends HttpServlet
{

   private static final long serialVersionUID = 1L;

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
      response.setContentType("text/html;charset=utf-8");
      response.addHeader("Access-Control-Allow-Origin", "*");
      response.setContentType("application/json");
      request.setCharacterEncoding("gbk");
      response.setCharacterEncoding("gbk");
      String dataString = "";
      String action = (String) request.getParameter("action");
      String no = (String) request.getParameter("no");
      
      if ("".equals(action))
      {
         /*request.setAttribute("status", "-1");
         request.setAttribute("content", "模块名不详！");
         request.getRequestDispatcher("/login.jsp").forward(request, response);*/
         PrintWriter out = response.getWriter();
         out.println("错误");
         out.close();
      }
      else
      {
         try
         {
            if ("login".equals(action)){
                  new LoginEntry().execute(request,response);
            }else if("user".equals(action)){
               new UserEntry().execute(request,response);
            }else if("build".equals(action)){
               new BuildEntry().execute(request,response);
            }else if("dorm".equals(action)){
               new DormEntry().execute(request,response);
            }else  if("student".equals(action)){
               new StudentEntry().execute(request, response);
            }else  if("admin".equals(action)){
               new AdminEntry().execute(request, response);
            }else  if("record".equals(action)){
               new RecordEntry().execute(request, response);
            }
            
            else
            {
               dataString = "{\"status\":\"-1\",\"content\":\"模块名有误！\"}";
            }
         }
         catch (Exception e)
         {
           e.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      doGet(req, resp);
   }

   public static JSONObject request2Json(HttpServletRequest request)
   {
      JSONObject requestJson = new JSONObject();
      Enumeration paramNames = request.getParameterNames();
      while (paramNames.hasMoreElements())
      {
         String paramName = (String) paramNames.nextElement();
         String[] pv = request.getParameterValues(paramName);
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < pv.length; i++)
         {
            if (pv[i].length() > 0)
            {
               if (i > 0)
               {
                  sb.append(",");
               }
               sb.append(pv[i]);
            }
         }
         requestJson.put(paramName, sb.toString());
      }
      return requestJson;
   }

   /* @param request
     * @return
     * @throws IOException
     */
   public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException
   {
      int contentLength = request.getContentLength();
      if (contentLength < 0)
      {
         return null;
      }
      byte buffer[] = new byte[contentLength];
      for (int i = 0; i < contentLength;)
      {
         int readlen = request.getInputStream().read(buffer, i, contentLength - i);
         if (readlen == -1)
         {
            break;
         }
         i += readlen;
      }
      return buffer;
   }

   public static JSONObject getRequestPostStr(HttpServletRequest request) throws IOException
   {
      byte buffer[] = getRequestPostBytes(request);
      String charEncoding = request.getCharacterEncoding();
      if (charEncoding == null)
      {
         charEncoding = "UTF-8";
      }

      //将json字符串转换为json对象   
      JSONObject json = JSONObject.fromObject(new String(buffer, charEncoding));
      return json;
   }

}
