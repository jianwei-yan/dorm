package main.user.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.internal.ws.message.StringHeader;

import main.admin.vo.ManageBuildRelVO;
import main.user.vo.UserVO;

import common.dao.BaseDAO;
import common.util.StringHelper;

public class UserDaoImpl extends BaseDAO implements UserDao {
    //根据账号、密码来查询
	public UserVO findByNoAndPwd(String no, String pwd) {
	      UserVO userVO=null;
	      try
	      {
	         String sqlStr = "select * from user where no=? and password=? ";
	         ps = this.getWrappedConnection().prepareStatement(sqlStr);
	         ps.setString(1, no);
	         ps.setString(2, pwd);
	         rs = ps.executeQuery();
	         if (rs.next())
	         {
	            userVO =  new UserVO();
	            userVO.setId(rs.getString("id"));
	            userVO.setName(rs.getString("name"));
	            userVO.setPwd(rs.getString("password"));
	            userVO.setNo(rs.getString("no"));
	            userVO.setDorm_no(rs.getString("dorm_no"));
	            userVO.setBuild_id(rs.getString("build_id"));
	            userVO.setSex(rs.getString("sex"));
	            userVO.setPhone(rs.getString("phone"));
	            userVO.setRole_id(rs.getString("role_id"));
	            userVO.setCreator(rs.getString("creator"));
	            
	         }
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }finally{
	         try
	         {
	            this.cleanUp();
	         }
	         catch (Exception e)
	         {
	            e.printStackTrace();
	         }
	      }
	      return userVO; 
	  
	   }
	//根据账号来查询
	   public UserVO queryAdminByAjaxWithId(String id)
	   {
	      UserVO userVO=null;
	      try
	      {
	         String sqlStr = "select a.*, (select GROUP_CONCAT(CAST(d.build_id as char)) from manage_build_rel d where d.user_id=a.id GROUP BY d.user_id)build_id1," +
	         "(select GROUP_CONCAT(c.name) from manage_build_rel d,build c where d.user_id=a.id and c.isValid='Y' and d.build_id=c.id GROUP BY d.user_id)build_name" +
	         " from user a where 1=1 and a.id=? ";
	         ps = this.getWrappedConnection().prepareStatement(sqlStr);
	         ps.setString(1, id);
	         rs = ps.executeQuery();
	         if (rs.next())
	         {
	            userVO =  new UserVO();
	            userVO.setId(rs.getString("id"));
	            userVO.setName(rs.getString("name"));
	            userVO.setPwd(rs.getString("password"));
	            userVO.setNo(rs.getString("no"));
	            userVO.setDorm_no(rs.getString("dorm_no"));
	            userVO.setBuild_id(rs.getString("build_id1"));
	            userVO.setBuild_name(rs.getString("build_name"));
	            userVO.setSex(rs.getString("sex"));
	            userVO.setPhone(rs.getString("phone"));
	            userVO.setRole_id(rs.getString("role_id"));
	            userVO.setCreator(rs.getString("creator"));
	         }
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }finally{
	         try
	         {
	            this.cleanUp();
	         }
	         catch (Exception e)
	         {
	            e.printStackTrace();
	         }
	      }
	      return userVO; 
	  
	}
	//根据账号来查询
   public UserVO queryByAjaxWithId(String id)
   {
      UserVO userVO=null;
      try
      {
         String sqlStr = "select a.* from user a where a.id=? ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         rs = ps.executeQuery();
         if (rs.next())
         {
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      return userVO; 
  
}
   public Map add(UserVO userVO)
   {
      String status="-1";
      String content="";
      String id="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "insert into user(name,password,no,dorm_no,build_id,sex,phone,role_id) values (?,?,?,?,?,?,?,?)";
         ps = this.getWrappedConnection().prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
         ps.setString(1, userVO.getName());
         ps.setString(2, userVO.getPwd());
         ps.setString(3, userVO.getNo());
         ps.setString(4, userVO.getDorm_no());
         ps.setString(5, userVO.getBuild_id());
         ps.setString(6, userVO.getSex());
         ps.setString(7, userVO.getPhone());
         ps.setString(8, userVO.getRole_id());
         int state = ps.executeUpdate();
         
         ResultSet generatedKeys = ps.getGeneratedKeys();
         if (generatedKeys.next()) {
            id=String.valueOf(generatedKeys.getInt(1));//主键
         }
         
         if(state>0){
            status="1";
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      retMap.put("status", status);
      retMap.put("content", content);
      retMap.put("id", id);
      return retMap; 
  }
   public Map mod(UserVO userVO)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "update user set name=?,password=?,no=?,dorm_no=?,build_id=?,sex=?,phone=?,role_id=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, userVO.getName());
         ps.setString(2, userVO.getPwd());
         ps.setString(3, userVO.getNo());
         ps.setString(4, userVO.getDorm_no());
         ps.setString(5, userVO.getBuild_id());
         ps.setString(6, userVO.getSex());
         ps.setString(7, userVO.getPhone());
         ps.setString(8, userVO.getRole_id());
         ps.setString(9, userVO.getId());
         int state = ps.executeUpdate();
         if(state>0){
            status="1";
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      
      
      retMap.put("status", status);
      retMap.put("content", content);
      return retMap; 
  }
   
   public Map del(String id)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "delete from user where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         int state = ps.executeUpdate();
         if(state>0){
            status="1";
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      
      
      retMap.put("status", status);
      retMap.put("content", content);
      return retMap; 
  }
   
   public Map queryByAjax(UserVO vo)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "select a.* from user a where 1=1 ";
         String build_id =vo.getBuild_id();
         if(!"".equals(build_id)){
            sqlStr +=" and a.build_id ='"+build_id+"'";
         }
         sqlStr +="order by a.id ";
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         UserVO userVO = null;
         while (rs.next())
         {
            userVO = new UserVO();
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
            list.add(userVO);
         }
         retMap.put("list", list);
         status="1";
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      
      retMap.put("status", status);
      retMap.put("content", content);
      return retMap; 
     
   }
   public Map queryByPage(UserVO vo, int start, int pageSize)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStrCount = "select COUNT(1) from user a where 1=1";
         String sqlStr = "select a.*,(select b.name from build b where b.id=a.build_id and b.isValid='Y')build_name," +
         		"(select d.name from dorm d where d.id=a.dorm_no)dorm_name from  user a where 1=1 ";
         
         String admin_user_id = StringHelper.convertStringNull(vo.getAdmin_user_id());
         if(!"".equals(admin_user_id)){//如果是宿舍管理员，则只能查询出他所管理的宿舍下的学生
            sqlStrCount += " and a.build_id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
            sqlStr += " and a.build_id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
         }
         
         String name =vo.getName();
         String build_id =vo.getBuild_id();
         String role_id =vo.getRole_id();
         String phone = vo.getPhone();
         String sex = vo.getSex();
         String dorm_no = vo.getDorm_no();
         
         if(!"".equals(role_id)){
            sqlStrCount +=" and a.role_id ='"+role_id+"'";
            sqlStr +=" and a.role_id ='"+role_id+"'";
         }
         if(!"".equals(name)){
            sqlStrCount +=" and a.name like'%"+name+"%'";
            sqlStr +=" and a.name like'%"+name+"%'";
         }
         if(!"".equals(build_id)){
            sqlStrCount +=" and a.build_id='"+build_id+"'";
            sqlStr +=" and a.build_id='"+build_id+"'";
         }
         if(!"".equals(phone)){
            sqlStrCount +=" and a.phone like'%"+phone+"%'";
            sqlStr +=" and a.phone like'%"+phone+"%'";
         }
         if(!"".equals(dorm_no)){
            sqlStrCount +=" and a.dorm_no='"+dorm_no+"'";
            sqlStr +=" and a.dorm_no='"+dorm_no+"'";
         }
         if(!"".equals(sex)){
            sqlStrCount +=" and a.sex='"+sex+"'";
            sqlStr +=" and a.sex='"+sex+"'";
         }
         sqlStr +="order by a.id limit "+start+", "+pageSize ;
         
         ps = this.getWrappedConnection().prepareStatement(sqlStrCount);
         rs = ps.executeQuery();
         int totalCount=0;
         if (rs.next())
         {
            totalCount = rs.getInt(1);
         }
         retMap.put("totalCount", String.valueOf(totalCount));
         rs=null;
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         UserVO userVO = null;
         while (rs.next())
         {
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setDorm_name(rs.getString("dorm_name"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setBuild_name(rs.getString("build_name"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
            
            list.add(userVO);
         }
         retMap.put("list", list);
         status="1";
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      
      
      retMap.put("status", status);
      retMap.put("content", content);
      return retMap; 
  
   }
   //管理员信息查询
   public Map queryAdminByPage(UserVO vo, int start, int pageSize)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStrCount = "select COUNT(1) name from user a where 1=1";
         String sqlStr = "select a.*, (select GROUP_CONCAT(CAST(d.build_id as char)) from manage_build_rel d where d.user_id=a.id GROUP BY d.user_id)build_id," +
               "(select GROUP_CONCAT(c.name) from manage_build_rel d,build c where d.user_id=a.id and c.isValid='Y' and d.build_id=c.id GROUP BY d.user_id)build_name" +
               " from user a where 1=1 ";
         String name =vo.getName();
         String build_id =vo.getBuild_id();
         String role_id =vo.getRole_id();
         String phone = vo.getPhone();
         String sex = vo.getSex();
         
         if(!"".equals(role_id)){
            sqlStrCount +=" and a.role_id ='"+role_id+"'";
            sqlStr +=" and a.role_id ='"+role_id+"'";
         }
         if(!"".equals(name)){
            sqlStrCount +=" and a.name like'%"+name+"%'";
            sqlStr +=" and a.name like'%"+name+"%'";
         }
         if(!"".equals(build_id)){
            sqlStrCount +=" and a.build_id='"+build_id+"'";
            sqlStr +=" and a.build_id='"+build_id+"'";
         }
         if(!"".equals(phone)){
            sqlStrCount +=" and a.phone like'%"+phone+"%'";
            sqlStr +=" and a.phone like'%"+phone+"%'";
         }
         if(!"".equals(sex)){
            sqlStrCount +=" and a.sex='"+sex+"'";
            sqlStr +=" and a.sex='"+sex+"'";
         }
         sqlStr +="order by a.id limit "+start+", "+pageSize ;
         
         ps = this.getWrappedConnection().prepareStatement(sqlStrCount);
         rs = ps.executeQuery();
         int totalCount=0;
         if (rs.next())
         {
            totalCount = rs.getInt(1);
         }
         retMap.put("totalCount", String.valueOf(totalCount));
         rs=null;
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         UserVO userVO = null;
         while (rs.next())
         {
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setBuild_name(rs.getString("build_name"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
            
            list.add(userVO);
         }
         retMap.put("list", list);
         status="1";
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      
      
      retMap.put("status", status);
      retMap.put("content", content);
      return retMap; 
  
   }
   
   public void addRel(ManageBuildRelVO manageBuildRelVO)
   {
      try
      {
         String sqlStr = "insert into manage_build_rel(build_id,user_id) values (?,?)";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, manageBuildRelVO.getBuild_id());
         ps.setString(2, manageBuildRelVO.getUser_id());
         ps.executeUpdate();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
  }
   public void delRel(String id)
   {
      try
      {
         String sqlStr = "delete from manage_build_rel where user_id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         ps.executeUpdate();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
  }
   public UserVO queryByAjaxWithNo(String no)
   {
      UserVO userVO=null;
      try
      {
         String sqlStr = "select * from user where no=? ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, no);
         rs = ps.executeQuery();
         if (rs.next())
         {
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      return userVO; 
  

   }
   public String queryStudentCountByAjax(String id)
   {
      int count=0;
      try
      {
         String sqlStr = "select count(1) from user where dorm_no='"+id+"' ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         if (rs.next())
         {
            count = rs.getInt(1);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      return String.valueOf(count); 
  

   }
   public List queryStudentsByAjax(String id)
   {
      List list = new ArrayList();
      try
      {
         String sqlStr = "select a.* from user a where dorm_no='"+id+"' ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         UserVO userVO = null;
         while (rs.next())
         {
            userVO = new UserVO();
            userVO =  new UserVO();
            userVO.setId(rs.getString("id"));
            userVO.setName(rs.getString("name"));
            userVO.setPwd(rs.getString("password"));
            userVO.setNo(rs.getString("no"));
            userVO.setDorm_no(rs.getString("dorm_no"));
            userVO.setBuild_id(rs.getString("build_id"));
            userVO.setSex(rs.getString("sex"));
            userVO.setPhone(rs.getString("phone"));
            userVO.setRole_id(rs.getString("role_id"));
            userVO.setCreator(rs.getString("creator"));
            list.add(userVO);
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
      return list; 
   }
   //修改密码
   public void modPassword(UserVO userVO)
   {
      try
      {
         String sqlStr = "update user set password=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, userVO.getPwd());
         ps.setString(2, userVO.getId());
         ps.executeUpdate();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }finally{
         try
         {
            this.cleanUp();
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
  }

}
