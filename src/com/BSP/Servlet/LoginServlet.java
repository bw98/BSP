package com.BSP.Servlet;

<<<<<<< HEAD
=======
import com.BSP.Service.UserService;
import com.BSP.Util.JWTUtil;
import com.BSP.bean.User;
import net.sf.json.JSONObject;

>>>>>>> 6fdc48eb556ee963280fedcd765b43f3caba01a7
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< HEAD


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
		
	/*	User _user=new User(name, password, tel, status, id);
		_user.setPassword(request.getParameter("password"));*/
		
		 
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
=======
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //接受json并转化为 User
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) req.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer("");
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String jsonStr = sb.toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        User u = (User) JSONObject.toBean(jsonObject, User.class);

        //执行登陆业务
        UserService userService = new UserService();
        int code = userService.login(u);


        //设置 token 并传输 json
        if ((code == 3) || (code == 4)) {
            try {
                Map<String, Object> payload = new HashMap<String, Object>();
                payload.put("user_name", u.getUserName());
                String token = JWTUtil.createJWT(u, 60000000, payload);
                Map<String, String> map = new HashMap<String, String>();
                map.put("token", token);
                map.put("status", Integer.toString(code));
                JSONObject jsonMap = JSONObject.fromObject(map);
                resp.getWriter().print(jsonMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
>>>>>>> 6fdc48eb556ee963280fedcd765b43f3caba01a7

}
