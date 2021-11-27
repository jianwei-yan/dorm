package main.build.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import common.dao.BaseDAO;
import main.build.vo.BuildVO;
import main.user.vo.UserVO;

public class BuildDaoImpl extends BaseDAO implements BuildDao
{
   public Map queryByPage(BuildVO buildVO, int start, int pageSize)
   {

      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStrCount = "select count(*) from build a where 1=1 ";
         String sqlStr = "select a.* from  build a where 1=1 ";
         String name =buildVO.getName();
         String isValid =buildVO.getIsValid();
         if(!"".equals(name)){
            sqlStrCount +=" and a.name like'%"+name+"%'";
            sqlStr +=" and a.name like'%"+name+"%'";
         }
         if(!"".equals(isValid)){
            sqlStrCount +=" and a.isValid='"+isValid+"'";
            sqlStr +=" and a.isValid='"+isValid+"'";
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
         BuildVO vo = null;
         while (rs.next())
         {
            vo = new BuildVO();
            vo.setId(rs.getString("id"));
            vo.setName(rs.getString("name"));
            vo.setRemark(rs.getString("remark"));
            vo.setIsValid(rs.getString("isValid"));
            list.add(vo);
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

   public Map add(BuildVO vo){
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "insert into build(name,remark,isvalid) values (?,?,?)";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, vo.getName());
         ps.setString(2, vo.getRemark());
         ps.setString(3, vo.getIsValid());
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
   
   public Map mod(BuildVO vo){
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "update build set name=?,remark=?,isValid=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, vo.getName());
         ps.setString(2, vo.getRemark());
         ps.setString(3, vo.getIsValid());
         ps.setString(4, vo.getId());
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
   

   public Map del(String id){
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "update build set isValid=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, "N");
         ps.setString(2, id);
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
 //根据账号来查询
   public BuildVO queryByAjaxWithId(String id)
   {
    
      BuildVO buildVO=null;
      try
      {
         String sqlStr = "select * from build where id=? ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         rs = ps.executeQuery();
         if (rs.next())
         {
            buildVO =  new BuildVO();
            buildVO.setId(rs.getString("id"));
            buildVO.setName(rs.getString("name"));
            buildVO.setRemark(rs.getString("remark"));
            buildVO.setIsValid(rs.getString("isValid"));
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
      return buildVO; 
     
   }

   public Map queryByAjax(BuildVO buildVO,String user_id)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "select a.* from  build a where 1=1 ";
         if(!"".equals(user_id)){//如果是宿舍管理员，则只能查询出他所管理的宿舍
            sqlStr += " and EXISTS (select 1 from manage_build_rel b where a.id=b.build_id and b.user_id='"+user_id+"')";
         }
         String name =buildVO.getName();
         String isValid =buildVO.getIsValid();
         if(!"".equals(name)){
            sqlStr +=" and a.name like'%"+name+"%'";
         }
         if(!"".equals(isValid)){
            sqlStr +=" and a.isValid='"+isValid+"'";
         }
         sqlStr +="order by a.id ";
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         BuildVO vo = null;
         while (rs.next())
         {
            vo = new BuildVO();
            vo.setId(rs.getString("id"));
            vo.setName(rs.getString("name"));
            vo.setRemark(rs.getString("remark"));
            vo.setIsValid(rs.getString("isValid"));
            list.add(vo);
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

}
