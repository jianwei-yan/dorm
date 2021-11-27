package main.user.dao;

import java.util.List;
import java.util.Map;

import main.admin.vo.ManageBuildRelVO;
import main.user.vo.UserVO;

public interface UserDao {
	UserVO findByNoAndPwd(String no, String pwd);

    UserVO queryByAjaxWithId(String no);

   Map queryByAjax(UserVO userVO);

   Map mod(UserVO userVO);

   Map del(String id);

   Map queryByPage(UserVO vo, int start, int pageSize);
   
   Map queryAdminByPage(UserVO vo, int start, int pageSize);

   Map add(UserVO userVO);

   void delRel(String id);

   void addRel(ManageBuildRelVO manageBuildRelVO);

   UserVO queryByAjaxWithNo(String no);

   UserVO queryAdminByAjaxWithId(String id);

   String queryStudentCountByAjax(String id);

   List queryStudentsByAjax(String id);

   void modPassword(UserVO userVO);
}
