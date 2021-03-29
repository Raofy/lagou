package com.fuyi.base;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 获取methodName参数
        String methodName = request.getParameter("methodName");

        // 2. 通过反射机制寻找对应servlet类的方法(优化代码，提高维护性)
        if (null != methodName) {

            // 这里this指的是继承BaseServlet的具体子类Servlet
            Class c = this.getClass();

            try {
                // 获取对应的方法
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                // 执行对应的方法
                method.invoke(this, request, response);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("没有对用的方法");
            }
        }
    }
}
