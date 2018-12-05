package com.BSP.Servlet;

import com.BSP.bean.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 将前端数据存入_user中
		 * 调用userservice的login方法
		 * 如果正常则把信息存入session保持登陆状态
		 * 如果异常则将错误信息存储到request域中并转发回登录界面
		 */
		
		User _user=new User();
		_user.setPassword(request.getParameter("password"));
		
		 
		/* UserService userservice=new UserService();
		try {
			User user=userservice.login(_user);
			request.getSession().setAttribute("SessionUser",user);
			if((user.getUsername()).equals("admin")){
				response.getWriter().print(1);
			}else{
				System.out.println("11111111111111111111");
			response.getWriter().print(2);
			}
			
			} catch (UserException e) {
			request.setAttribute("error",e.getMessage());;
			response.getWriter().print(3);
		}*/
		 
		 
		 
	}

}
