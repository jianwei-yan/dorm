package main.record.dao;

import java.util.Map;

import main.record.vo.RecordVO;

public interface RecordDao {
   Map queryByAjax(RecordVO RecordVO);

   Map mod(RecordVO RecordVO);

   Map del(String id);

   Map queryByPage(RecordVO vo, int start, int pageSize);

   Map add(RecordVO RecordVO);

   RecordVO queryByAjaxWithNo(String no);

   RecordVO queryByAjaxWithId(String id);

}
