package main.user.service;

import java.util.List;
import java.util.Map;

import main.admin.vo.ManageBuildRelVO;
import main.user.dao.UserDao;
import main.user.dao.UserDaoImpl;
import main.user.vo.UserVO;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	public UserVO findByNoAndPwd(String no, String pwd) {
		return userDao.findByNoAndPwd(no,pwd);
	}
   public UserVO queryByAjaxWithId(String no)
   {
      return userDao.queryByAjaxWithId(no);
   }
   public Map queryByPage(UserVO vo, int start, int pageSize)
   {
      return userDao.queryByPage(vo,start,pageSize);
   }
   public Map add(UserVO userVO)
   {
      return userDao.add(userVO);
   }
   public Map del(String id)
   {
      return userDao.del(id);
   }
   public Map mod(UserVO userVO)
   {
      return userDao.mod(userVO);
   }
   public Map queryByAjax(UserVO userVO)
   {
      return userDao.queryByAjax(userVO);
   }
   public void addRel(ManageBuildRelVO manageBuildRelVO)
   {
      userDao.addRel(manageBuildRelVO);
   }
   public void delRel(String id)
   {
      userDao.delRel(id);      
   }
   public Map queryAdminByPage(UserVO vo, int start, int pageSize)
   {
      return userDao.queryAdminByPage(vo,start,pageSize);
   }
   public UserVO queryByAjaxWithNo(String no)
   {
      return userDao.queryByAjaxWithNo(no);
   }
   public UserVO queryAdminByAjaxWithId(String id)
   {
      return userDao.queryAdminByAjaxWithId(id);
   }
   public String queryStudentCountByAjax(String id)
   {
      return userDao.queryStudentCountByAjax(id);
   }
   public List queryStudentsByAjax(String id)
   {
      return userDao.queryStudentsByAjax(id);
   }
   public void modPassword(UserVO userVO1)
   {
      userDao.modPassword(userVO1);
   }

}
