package main.dorm.dao;

import java.util.Map;
import main.dorm.vo.DormVO;

public interface DormDao
{

   Map queryByPage(DormVO vo, int start, int pageSize);

   Map add(DormVO dormVO);

   Map del(String id);

   Map mod(DormVO dormVO);

   DormVO queryByAjaxWithId(String id);

   Map queryByAjax(DormVO dormVO);

}
