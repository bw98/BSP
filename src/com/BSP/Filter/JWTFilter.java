package com.BSP.Filter;

import com.BSP.Util.JWTUtil;
import io.jsonwebtoken.Claims;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "JWTFilter")
public class JWTFilter implements Filter {

    private Set<String> prefixIignores = new HashSet<String>();

    private boolean canIgnore(HttpServletRequest request) {
        String url = request.getRequestURI();
        for (String ignore : prefixIignores) {
            System.out.println("url------------->"+url);
            if (url.startsWith(ignore)) {

                return true;
            }
        }
        return false;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        if (canIgnore(request)) {
            chain.doFilter(request, response);
            return;
        }

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
        String cp = config.getServletContext().getContextPath();
        String ignoresParam = config.getInitParameter("ignores");
        String[] ignoreArray = ignoresParam.split(",");
        for (String s : ignoreArray) {
            prefixIignores.add(cp + s);
        }
    }
}
