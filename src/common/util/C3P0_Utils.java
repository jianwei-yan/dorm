package common.util;
 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import com.mchange.v2.c3p0.ComboPooledDataSource;
 
 public class C3P0_Utils {
     
     private static ComboPooledDataSource dataSource = null;
     //在静态代码块中创建数据库连接池
     static{
         try{
             dataSource = new ComboPooledDataSource("MySQL");//使用C3P0的命名配置来创建数据源
             
         }catch (Exception e) {
             throw new ExceptionInInitializerError(e);
         }
     }
     
     public static Connection getConnection() throws SQLException{
         //从数据源中获取数据库连接
         return dataSource.getConnection();
     }
     
     // 释放资源
     public static void release(Connection conn,Statement st,ResultSet rs){
         if(rs!=null){
             try{
                 //关闭存储查询结果的ResultSet对象
                 rs.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
             rs = null;
         }
         if(st!=null){
             try{
                 //关闭负责执行SQL命令的Statement对象
                 st.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         
         if(conn!=null){
             try{
                 //将Connection连接对象还给数据库连接池
                 conn.close();
             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
 }