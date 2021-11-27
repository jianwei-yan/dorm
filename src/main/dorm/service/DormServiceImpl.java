package main.dorm.service;

import java.util.Map;

import main.dorm.dao.DormDao;
import main.dorm.dao.DormDaoImpl;
import main.dorm.vo.DormVO;

public class DormServiceImpl implements DormService
{
   private DormDao dormDao = new DormDaoImpl();
   public Map queryByPage(DormVO vo, int start, int pageSize)
   {
      return dormDao.queryByPage(vo, start, pageSize);
   }
   public Map add(DormVO DormVO)
   {
      return dormDao.add(DormVO);
   }
   public Map del(String id)
   {
      return dormDao.del(id);
   }
   public Map mod(DormVO DormVO)
   {
      return dormDao.mod(DormVO);
   }
   public DormVO queryByAjaxWithId(String id)
   {
      return dormDao.queryByAjaxWithId(id);
   }
   public Map queryByAjax(DormVO dormVO)
   {
      return dormDao.queryByAjax(dormVO);
   }

}
