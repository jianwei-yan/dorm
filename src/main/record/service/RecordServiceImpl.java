package main.record.service;

import java.util.Map;

import main.admin.vo.ManageBuildRelVO;
import main.record.dao.RecordDao;
import main.record.dao.RecordDaoImpl;
import main.record.vo.RecordVO;

public class RecordServiceImpl implements RecordService {

	private RecordDao recordDao = new RecordDaoImpl();
   public Map queryByPage(RecordVO vo, int start, int pageSize)
   {
      return recordDao.queryByPage(vo,start,pageSize);
   }
   public Map add(RecordVO RecordVO)
   {
      return recordDao.add(RecordVO);
   }
   public Map del(String id)
   {
      return recordDao.del(id);
   }
   public Map mod(RecordVO RecordVO)
   {
      return recordDao.mod(RecordVO);
   }
   public Map queryByAjax(RecordVO RecordVO)
   {
      return recordDao.queryByAjax(RecordVO);
   }
   public RecordVO queryByAjaxWithNo(String no)
   {
      return recordDao.queryByAjaxWithNo(no);
   }
   public RecordVO queryByAjaxWithId(String id)
   {
      return recordDao.queryByAjaxWithId(id);
   }
}
