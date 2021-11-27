package main.dorm.vo;

public class DormVO
{
   private String id;
   private String name;
   private String remark;
   private String build_id;
   private String build_name;
   private String max_num;
   private String admin_user_id;//传入的宿舍管理员ID，如果有则需要根据管理员权限查询

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getRemark()
   {
      return remark;
   }

   public String getBuild_id()
   {
      return build_id;
   }

   public void setBuild_id(String build_id)
   {
      this.build_id = build_id;
   }

   public String getMax_num()
   {
      return max_num;
   }

   public void setMax_num(String max_num)
   {
      this.max_num = max_num;
   }

   public void setRemark(String remark)
   {
      this.remark = remark;
   }

   public String getBuild_name()
   {
      return build_name;
   }

   public void setBuild_name(String build_name)
   {
      this.build_name = build_name;
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
