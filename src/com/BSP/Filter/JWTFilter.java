package com.BSP.Filter;

import com.BSP.Util.JWTUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "JWTFilter")
public class JWTFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String jwt = request.getHeader("Authorization");
        try {
            Claims c = JWTUtil.parseJWT(jwt);
            response.getWriter().write("User: " + c.get("user_name") + " has logged in");
            chain.doFilter(req, resp);

        } catch (Exception e) {
            response.getWriter().write("not login，validate fail");
            e.printStackTrace();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
