package common.util;

public class StringHelper
{
   public static final String convertStringNull(String strOrig)
   {
      String strReturn = "";
      if (strOrig == null || strOrig.equals(null))
      {
         strReturn = "";
      }
      else
      {
         strReturn = strOrig.trim();
      }
      return strReturn;
   }

}
