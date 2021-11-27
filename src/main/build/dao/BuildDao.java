package main.build.dao;

import java.util.Map;

import main.build.vo.BuildVO;

public interface BuildDao
{

   Map queryByPage(BuildVO vo, int start, int pageSize);

   Map add(BuildVO buildVO);

   Map del(String id);

   Map mod(BuildVO buildVO);

   BuildVO queryByAjaxWithId(String id);

   Map queryByAjax(BuildVO vo, String user_id);

}
