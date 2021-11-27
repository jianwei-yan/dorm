package main.record.vo;

import java.sql.Timestamp;

public class RecordVO
{
   private String id;
   private String student_id;
   private Timestamp record_date;
   private String record_type;
   private String remark;
   private String build_id;
   private String build_name;
   private String dorm_no;
   private String dorm_name;
   private String phone;
   private String name;
   private String admin_user_id;//传入的宿舍管理员ID，如果有则需要根据管理员权限查询
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getStudent_id()
   {
      return student_id;
   }

   public void setStudent_id(String student_id)
   {
      this.student_id = student_id;
   }

   public Timestamp getRecord_date()
   {
      return record_date;
   }

   public void setRecord_date(Timestamp record_date)
   {
      this.record_date = record_date;
   }

   public String getRecord_type()
   {
      return record_type;
   }

   public void setRecord_type(String record_type)
   {
      this.record_type = record_type;
   }

   public String getRemark()
   {
      return remark;
   }

   public void setRemark(String remark)
   {
      this.remark = remark;
   }

   public String getBuild_id()
   {
      return build_id;
   }

   public void setBuild_id(String build_id)
   {
      this.build_id = build_id;
   }

   public String getBuild_name()
   {
      return build_name;
   }

   public void setBuild_name(String build_name)
   {
      this.build_name = build_name;
   }

   public String getDorm_no()
   {
      return dorm_no;
   }

   public void setDorm_no(String dorm_no)
   {
      this.dorm_no = dorm_no;
   }

   public String getDorm_name()
   {
      return dorm_name;
   }

   public void setDorm_name(String dorm_name)
   {
      this.dorm_name = dorm_name;
   }

   public String getPhone()
   {
      return phone;
   }

   public void setPhone(String phone)
   {
      this.phone = phone;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getAdmin_user_id()
   {
      return admin_user_id;
   }

   public void setAdmin_user_id(String admin_user_id)
   {
      this.admin_user_id = admin_user_id;
   }

}
