package main.build.service;

import java.util.Map;

import main.build.vo.BuildVO;

public interface BuildService
{

   Map queryByPage(BuildVO vo, int start, int pageSize);

   Map mod(BuildVO buildVO);

   Map add(BuildVO buildVO);

   Map del(String id);

   BuildVO queryByAjaxWithId(String id);

   Map queryByAjax(BuildVO vo, String user_id);

}
