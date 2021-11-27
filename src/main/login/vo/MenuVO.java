package main.login.vo;

import java.util.List;

public class MenuVO
{
   private String id;
   private String menuCode;
   private String menuName;
   private String menuLevel;
   private String menuParentCode;
   private String menuClick;
   private String menuRight;
   private List childs;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getMenuCode()
   {
      return menuCode;
   }

   public void setMenuCode(String menuCode)
   {
      this.menuCode = menuCode;
   }

   public String getMenuName()
   {
      return menuName;
   }

   public void setMenuName(String menuName)
   {
      this.menuName = menuName;
   }

   public String getMenuLevel()
   {
      return menuLevel;
   }

   public void setMenuLevel(String menuLevel)
   {
      this.menuLevel = menuLevel;
   }

   public String getMenuParentCode()
   {
      return menuParentCode;
   }

   public void setMenuParentCode(String menuParentCode)
   {
      this.menuParentCode = menuParentCode;
   }

   public String getMenuClick()
   {
      return menuClick;
   }

   public void setMenuClick(String menuClick)
   {
      this.menuClick = menuClick;
   }

   public String getMenuRight()
   {
      return menuRight;
   }

   public void setMenuRight(String menuRight)
   {
      this.menuRight = menuRight;
   }

   public List getChilds()
   {
      return childs;
   }

   public void setChilds(List childs)
   {
      this.childs = childs;
   }

}
