package main.dorm.service;

import java.util.Map;

import main.dorm.vo.DormVO;


public interface DormService
{

   Map queryByPage(DormVO vo, int start, int pageSize);

   Map mod(DormVO dormVO);

   Map add(DormVO dormVO);

   Map del(String id);

   DormVO queryByAjaxWithId(String id);

   Map queryByAjax(DormVO dormVO);

}
