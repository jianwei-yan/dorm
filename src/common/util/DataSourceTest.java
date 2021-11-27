package common.util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataSourceTest {

    public void c3p0DataSourceTest() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            //获取数据库连接
            conn = C3P0_Utils.getConnection();
            String sql = "select * from user ";
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next()){
               
               System.out.println("名字："+rs.getString("name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            //释放资源
        	C3P0_Utils.release(conn, st, rs);
        }
    }
    
    public static void main(String[] args) {
    	new DataSourceTest().c3p0DataSourceTest();
	}
}