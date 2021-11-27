package main.build.vo;

public class BuildVO
{
   private String id;
   private String name;
   private String remark;
   private String isValid;

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

   public void setRemark(String remark)
   {
      this.remark = remark;
   }

   public String getIsValid()
   {
      return isValid;
   }

   public void setIsValid(String isValid)
   {
      this.isValid = isValid;
   }
}
