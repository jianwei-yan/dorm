package main.record.service;

import java.util.Map;

import main.admin.vo.ManageBuildRelVO;
import main.record.vo.RecordVO;

public interface RecordService {

    Map queryByPage(RecordVO vo, int start, int pageSize);
    Map del(String id);
    Map add(RecordVO RecordVO);
    Map mod(RecordVO RecordVO);
    Map queryByAjax(RecordVO RecordVO);
   RecordVO queryByAjaxWithNo(String no);
   RecordVO queryByAjaxWithId(String id);
}
