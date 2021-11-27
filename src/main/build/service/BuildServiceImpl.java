package main.build.service;

import java.util.Map;

import main.build.dao.BuildDao;
import main.build.dao.BuildDaoImpl;
import main.build.vo.BuildVO;

public class BuildServiceImpl implements BuildService
{
   private BuildDao buildDao = new BuildDaoImpl();
   public Map queryByPage(BuildVO vo, int start, int pageSize)
   {
      return buildDao.queryByPage(vo, start, pageSize);
   }
   public Map add(BuildVO buildVO)
   {
      return buildDao.add(buildVO);
   }
   public Map del(String id)
   {
      return buildDao.del(id);
   }
   public Map mod(BuildVO buildVO)
   {
      return buildDao.mod(buildVO);
   }
   public BuildVO queryByAjaxWithId(String id)
   {
      return buildDao.queryByAjaxWithId(id);
   }
   public Map queryByAjax(BuildVO vo,String user_id)
   {
      return buildDao.queryByAjax(vo,user_id);
   }

}
