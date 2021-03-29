package com.fuyi.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * doGet()方法作为调度器 控制器,根据请求的功能不同,调用对应的方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 获取get请求中的参数methodName
        String methodName = request.getParameter("methodName");

        // 2. 通过method进行执行不同的处理逻辑
        if ("add".equals(methodName)) {
            this.addCourse(request, response);
        } else if ("update".equals(methodName)) {
            this.updateCourse(request, response);
        } else if ("delete".equals(methodName)) {
            this.deleteCourse(request, response);
        } else {
            System.out.println("没有该操作");
        }
    }


    private void addCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行增加操作");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("更新操作");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除操作");
    }
}
