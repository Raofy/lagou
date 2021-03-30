package com.fuyi.student.servlet;



import com.fuyi.student.model.User;
import com.fuyi.student.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/loginServlet","/mainServlet"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取请求路径
        StringBuffer requestURL = request.getRequestURL();
        //切割获取对应的urlPatterns
        String action=requestURL.substring(requestURL.lastIndexOf("/")+1);
        //登录的请求
        if (action.equals("loginServlet")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String value=request.getParameter("remember");

            UserService userService = new UserService();
            User user = userService.userLoginService(new User(username, password));
            if (null != user) {
                System.out.println("登录成功");
                //创建Cookie
                Cookie cookie=new Cookie("userinfo", username+"-"+password);
                //设置Cokie的生命周期
                //如果选中的话的就是设置7天的有效时间否则设置为0
                int MaxAge="1".equals(value)?24*60*60*7:0;
                //设置生命周期
                cookie.setMaxAge(MaxAge);
                //将cookie添加到response中
                response.addCookie(cookie);
                //将登陆成功的信息放入session对象中保存 实现多个请求共享
                //response.sendRedirect("main.jsp");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.jsp");
                request.getSession().setAttribute("main","mainPage");
                request.getSession().setAttribute("user", username);
                requestDispatcher.forward(request, response);

            } else {
                System.out.println("登陆失败");
                //实现服务器的跳转返回首页
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
                requestDispatcher.forward(request, response);
            }
        }else if (action.equals("mainServlet")){
            //如果是请求主页面的话 服务器转发
            request.getSession().setAttribute("main","mainPage");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.jsp");
            requestDispatcher.forward(request,response);

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
