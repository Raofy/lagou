package com.fuyi.web.servlet;

import com.fuyi.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet2", urlPatterns = "/test2")
public class TestServlet2 extends BaseServlet {

    public void addCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行增加操作");
    }

    public void updateCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("更新操作");
    }

    public void deleteCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除操作");
    }
}
