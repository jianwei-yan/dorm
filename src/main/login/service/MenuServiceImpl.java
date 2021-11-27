package main.login.service;

import java.util.ArrayList;
import java.util.List;

import main.login.dao.MenuDao;
import main.login.dao.MenuDaoImpl;
import main.login.vo.MenuVO;

public class MenuServiceImpl implements MenuService {

	private MenuDao menudao = new MenuDaoImpl();
	public List getMenu(String type) {
	      //先取1级，再取2级
	      List list1 = menudao.getMenu(type,"1");
	      List list2 = menudao.getMenu(type,"2");
	      MenuVO menuVO1 = new MenuVO();
	      MenuVO menuVO2 = new MenuVO();
	      for(int i=0;i<list1.size();i++){
	         List childs = new ArrayList();
	         menuVO1 = (MenuVO)list1.get(i);
	         for(int j=0;j<list2.size();j++){
	            menuVO2 = (MenuVO)list2.get(j);
	            if(menuVO2.getMenuParentCode().equals(menuVO1.getMenuCode())){
	               childs.add(menuVO2);
	            }
	         }
	       //将级别为2的菜单添加到菜单1
	         menuVO1.setChilds(childs);
	      }
		return list1;
	}

}
