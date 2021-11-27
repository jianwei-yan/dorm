package main.servlet;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.user.vo.UserVO;
       
public class RightFilter implements Filter {        
       
    public void destroy() {        
    }        
       
    public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain filterChain) throws IOException, ServletException {        
        HttpServletRequest request=(HttpServletRequest)sreq;        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) sres;
        HttpSession session = req.getSession();
        
		UserVO userVO = (UserVO)session.getAttribute("CurrentUser");
		if(userVO!=null){//通过
			filterChain.doFilter(req, resp);
		}else{//跳转到登录页面
			req.getRequestDispatcher("/login.jsp").forward(sreq,sres);
		}
     }        
       
     public void init(FilterConfig arg0) throws ServletException {            
     }        
       
}  