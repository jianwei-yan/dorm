package main.login.dao;

import java.util.ArrayList;
import java.util.List;

import main.login.vo.MenuVO;

import common.dao.BaseDAO;

public class MenuDaoImpl extends BaseDAO implements MenuDao {

	public List getMenu(String type,String level) {
		 String sqlStr = "select a.* from menu a where a.menuLevel='"+level+"' ";
            	sqlStr +=" and a.menuRight like '%"+type+"%'";
            
        List list = new ArrayList();    	
    	try {
            ps = this.getWrappedConnection().prepareStatement(sqlStr);
            rs = ps.executeQuery();
            
            MenuVO vo = null;
            while (rs.next())
            {
               vo = new MenuVO();
               
               vo.setId(rs.getString("id"));
               vo.setMenuCode(rs.getString("menuCode"));
               vo.setMenuName(rs.getString("menuName"));
               vo.setMenuLevel(rs.getString("menuLevel"));
               vo.setMenuParentCode(rs.getString("menuParentCode"));
               vo.setMenuClick(rs.getString("menuClick"));
               vo.setMenuRight(rs.getString("menuRight"));
               list.add(vo);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
