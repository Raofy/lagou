package com.fuyi.student.servlet.StudentServlet;

import com.fuyi.student.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteServlet", urlPatterns = "/deleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = response.getWriter();
        //接收前端发送的数据
        String data = request.getParameter("username");
        String[] stus = data.split(",");
        StudentService service = new StudentService();
        int i = service.deletStudent(stus);
        if (i > 0) {
            System.out.println("删除成功");
            writer.println("删除成功");
        } else {
            System.out.println("删除失败");
            writer.println("请选择你要删除的学生");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}