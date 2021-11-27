package common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;

import common.util.C3P0_Utils;


public class BaseDAO
{
   protected PreparedStatement ps = null; 
   protected ResultSet rs = null; 
   private Connection customConn = null; 
   protected HashMap hashMap = null; 

   public void setWrappedConnection(Connection customConn)
   {
      this.customConn = customConn;
   }

   public void setConnection(Connection customConn, HashMap hashMap)
   {
      this.hashMap = hashMap;
      this.customConn = customConn;
   }

   public Connection getWrappedConnection()
   {
      if (customConn == null)
      {
         try
         {
            customConn = C3P0_Utils.getConnection();
         }
         catch (SQLException e)
         {
            e.printStackTrace();
         }
      }
      return customConn;
   }

   public void cleanUp() throws Exception
   {
      C3P0_Utils.release(customConn, ps, rs);
     /* try
      {
         if (rs != null)
         {
            rs.close();
            rs = null;
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         try
         {
            if (ps != null)
            {
               ps.close();
               ps = null;
            }
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }*/
   }
}
