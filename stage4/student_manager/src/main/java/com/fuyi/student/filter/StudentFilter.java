package com.fuyi.student.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StudentFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将请求进行一个强转
        HttpServletRequest httpServletRequest=(HttpServletRequest)servletRequest;
        Object Page = httpServletRequest.getSession().getAttribute("PageFilter");
        //获取请求的路径
        String servletPath = httpServletRequest.getServletPath();
        //判断请求路径和用户名
        if (null==Page){
            // 如果用户民为空和不包含login的访问路径那么就直接跳转到登录界面
            httpServletRequest.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
        }else {
            //如果满足条件的话就不拦截  放行
            doFilter(servletRequest, servletResponse, filterChain);
        }


    }

    @Override
    public void destroy() {

    }
}
