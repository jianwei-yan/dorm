package main.dorm.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import common.dao.BaseDAO;
import common.util.StringHelper;
import main.build.vo.BuildVO;
import main.dorm.vo.DormVO;

public class DormDaoImpl extends BaseDAO implements DormDao
{
   public Map queryByPage(DormVO dormVO, int start, int pageSize)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStrCount = "select COUNT(1) name from dorm a where 1=1";
         String sqlStr = "select a.*,(select b.name from build b where b.id=a.build_id and b.isValid='Y')build_name from  dorm a where 1=1 ";
         
         String admin_user_id = StringHelper.convertStringNull(dormVO.getAdmin_user_id());
         if(!"".equals(admin_user_id)){//如果是宿舍管理员，则只能查询出他所管理的宿舍下的学生
            sqlStrCount += " and a.build_id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
            sqlStr += " and a.build_id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
         }
         
         String name =dormVO.getName();
         String build_id =dormVO.getBuild_id();
         if(!"".equals(name)){
            sqlStrCount +=" and a.name like'%"+name+"%'";
            sqlStr +=" and a.name like'%"+name+"%'";
         }
         if(!"".equals(build_id)){
            sqlStrCount +=" and a.build_id='"+build_id+"'";
            sqlStr +=" and a.build_id='"+build_id+"'";
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
         DormVO vo = null;
         while (rs.next())
         {
            vo = new DormVO();
            vo.setId(rs.getString("id"));
            vo.setName(rs.getString("name"));
            vo.setMax_num(rs.getString("max_num"));
            vo.setBuild_name(rs.getString("build_name"));
            vo.setRemark(rs.getString("remark"));
            //vo.setIsValid(rs.getString("isValid"));
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

   public Map add(DormVO vo){
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "insert into dorm(name,remark,max_num,build_id) values (?,?,?,?)";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, vo.getName());
         ps.setString(2, vo.getRemark());
         ps.setString(3, vo.getMax_num());
         ps.setString(4, vo.getBuild_id());
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
   
   public Map mod(DormVO vo){
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "update dorm set name=?,remark=?,max_num=?,build_id=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, vo.getName());
         ps.setString(2, vo.getRemark());
         ps.setString(3, vo.getMax_num());
         ps.setString(4, vo.getBuild_id());
         ps.setString(5, vo.getId());
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
         String sqlStr = "delete from dorm where id=?";
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
 //根据账号来查询
   public DormVO queryByAjaxWithId(String id)
   {
    
      DormVO dormVO=null;
      try
      {
         String sqlStr = "select * from dorm where id=? ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         rs = ps.executeQuery();
         if (rs.next())
         {
            dormVO =  new DormVO();
            dormVO.setId(rs.getString("id"));
            dormVO.setName(rs.getString("name"));
            dormVO.setRemark(rs.getString("remark"));
            dormVO.setBuild_id(rs.getString("build_id"));
            dormVO.setMax_num(rs.getString("max_num"));
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
      return dormVO; 
     
   }

   public Map queryByAjax(DormVO dormVO)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "select a.* from  dorm a where 1=1 ";
         String build_id =dormVO.getBuild_id();
         if(!"".equals(build_id)){
            sqlStr +=" and a.build_id ='"+build_id+"'";
         }
         sqlStr +="order by a.id ";
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         DormVO vo = null;
         while (rs.next())
         {
            vo = new DormVO();
            vo.setId(rs.getString("id"));
            vo.setName(rs.getString("name"));
            vo.setRemark(rs.getString("remark"));
            vo.setMax_num(rs.getString("max_num"));
            vo.setBuild_id(rs.getString("build_id"));
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
