package main.record.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.admin.vo.ManageBuildRelVO;
import main.record.vo.RecordVO;

import common.dao.BaseDAO;
import common.util.StringHelper;

public class RecordDaoImpl extends BaseDAO implements RecordDao {
	//根据账号来查询
   public RecordVO queryByAjaxWithId(String id)
   {
      RecordVO recordVO=null;
      try
      {
         String sqlStr = "select a.*,b.name,b.phone,c.name dorm_name,c.id dorm_id,d.name build_name,d.id build_id from  Record a,user b,dorm c,build d where 1=1 "+
         " and a.student_id=b.id and c.id=b.dorm_no and b.build_id=d.id and d.isValid='Y' and a.id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, id);
         rs = ps.executeQuery();
         if (rs.next())
         {
            recordVO =  new RecordVO();
            recordVO.setId(rs.getString("id"));
            recordVO.setStudent_id(rs.getString("student_id"));
            recordVO.setRecord_date(rs.getTimestamp("date"));
            recordVO.setRecord_type(rs.getString("record_type"));
            recordVO.setRemark(rs.getString("remark"));
            recordVO.setBuild_id(rs.getString("build_id"));
            recordVO.setBuild_name(rs.getString("build_name"));
            recordVO.setDorm_no(rs.getString("dorm_id"));
            recordVO.setDorm_name(rs.getString("dorm_name"));
            recordVO.setName(rs.getString("name"));
            recordVO.setPhone(rs.getString("phone"));
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
      return recordVO; 
  
}
   public Map add(RecordVO recordVO)
   {
      String status="-1";
      String content="";
      String id="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "insert into Record(student_id,date,record_type,remark) values (?,?,?,?)";
         ps = this.getWrappedConnection().prepareStatement(sqlStr,Statement.RETURN_GENERATED_KEYS);
         ps.setString(1, recordVO.getStudent_id());
         ps.setTimestamp(2, recordVO.getRecord_date());
         ps.setString(3, recordVO.getRecord_type());
         ps.setString(4, recordVO.getRemark());
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
   public Map mod(RecordVO recordVO)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "update Record set student_id=?,date=?,record_type=?,remark=? where id=?";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, recordVO.getStudent_id());
         ps.setTimestamp(2, recordVO.getRecord_date());
         ps.setString(3, recordVO.getRecord_type());
         ps.setString(4, recordVO.getRemark());
         ps.setString(5, recordVO.getId());
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
         String sqlStr = "delete from Record where id=?";
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
   
   public Map queryByAjax(RecordVO vo)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStr = "select a.* from Record a where 1=1 ";
         String student_id =vo.getStudent_id();
         if(!"".equals(student_id)){
            sqlStr +=" and a.student_id ='"+student_id+"'";
         }
         sqlStr +="order by a.id ";
         
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         rs = ps.executeQuery();
         
         List list = new ArrayList();
         RecordVO recordVO = null;
         while (rs.next())
         {
            recordVO =  new RecordVO();
            recordVO.setId(rs.getString("id"));
            recordVO.setStudent_id(rs.getString("student_id"));
            recordVO.setRecord_date(rs.getTimestamp("record_date"));
            recordVO.setRecord_type(rs.getString("record_type"));
            recordVO.setRemark(rs.getString("remark"));
            list.add(recordVO);
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
   public Map queryByPage(RecordVO vo, int start, int pageSize)
   {
      String status="-1";
      String content="";
      Map retMap = new HashMap();
      try
      {
         String sqlStrCount = "select count(1) from  Record a,user b,dorm c,build d where 1=1 "+
                         " and a.student_id=b.id and c.id=b.dorm_no and b.build_id=d.id and d.isValid='Y' ";
         String sqlStr = "select a.*,b.name,b.phone,c.name dorm_name,d.name build_name from  Record a,user b,dorm c,build d where 1=1 "+
                         " and a.student_id=b.id and c.id=b.dorm_no and b.build_id=d.id and d.isValid='Y' ";
         
         String admin_user_id = StringHelper.convertStringNull(vo.getAdmin_user_id());
         if(!"".equals(admin_user_id)){//如果是宿舍管理员，则只能查询出他所管理的宿舍下的学生
            sqlStrCount += " and d.id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
            sqlStr += " and d.id in( select b.build_id from manage_build_rel b,user c where c.id=b.user_id and c.id='"+admin_user_id+"')";
         }
         
         String name=vo.getName();
         String phone=vo.getPhone();
         String build_id=vo.getBuild_id();
         String dorm_no=vo.getDorm_no();
         
         Timestamp date =vo.getRecord_date();
         String record_type = StringHelper.convertStringNull(vo.getRecord_type());
         
         if(!"".equals(name)){
            sqlStrCount +=" and b.name like'%"+name+"%'";
            sqlStr +=" and b.name like'%"+name+"%'";
         }
         if(!"".equals(phone)){
            sqlStrCount +=" and b.phone like'%"+phone+"%'";
            sqlStr +=" and b.phone like'%"+phone+"%'";
         }
         if(!"".equals(build_id)){
            sqlStrCount +=" and d.id ='"+build_id+"'";
            sqlStr +=" and d.id ='"+build_id+"'";
         }
         if(!"".equals(dorm_no)){
            sqlStrCount +=" and c.id ='"+dorm_no+"'";
            sqlStr +=" and c.id ='"+dorm_no+"'";
         }
         
         if(date!=null){
            sqlStrCount +=" and a.date='"+date+"'";
            sqlStr +=" and a.date='"+date+"'";
         }
         if(!"".equals(record_type)){
            sqlStrCount +=" and a.record_type='"+record_type+"'";
            sqlStr +=" and a.record_type='"+record_type+"'";
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
         RecordVO recordVO = null;
         while (rs.next())
         {
            recordVO =  new RecordVO();
            recordVO.setId(rs.getString("id"));
            recordVO.setStudent_id(rs.getString("student_id"));
            recordVO.setRecord_date(rs.getTimestamp("date"));
            recordVO.setRecord_type(rs.getString("record_type"));
            recordVO.setRemark(rs.getString("remark"));
            recordVO.setBuild_name(rs.getString("build_name"));
            recordVO.setDorm_name(rs.getString("dorm_name"));
            recordVO.setName(rs.getString("name"));
            recordVO.setPhone(rs.getString("phone"));
            list.add(recordVO);
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
 
   public RecordVO queryByAjaxWithNo(String no)
   {
      RecordVO recordVO=null;
      try
      {
         String sqlStr = "select * from Record where no=? ";
         ps = this.getWrappedConnection().prepareStatement(sqlStr);
         ps.setString(1, no);
         rs = ps.executeQuery();
         if (rs.next())
         {
            recordVO =  new RecordVO();
            recordVO.setId(rs.getString("id"));
            recordVO.setStudent_id(rs.getString("student_id"));
            recordVO.setRecord_date(rs.getTimestamp("record_date"));
            recordVO.setRecord_type(rs.getString("record_type"));
            recordVO.setRemark(rs.getString("remark"));
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
      return recordVO; 
  

   }

}
