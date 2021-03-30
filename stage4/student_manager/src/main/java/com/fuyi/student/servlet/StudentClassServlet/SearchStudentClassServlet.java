package com.fuyi.student.servlet.StudentClassServlet;


import com.fuyi.student.model.StudentClass;
import com.fuyi.student.service.StudentClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SearchStudentClassServlet", urlPatterns = "/searchStudentClassServlet")
public class SearchStudentClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String class_id = request.getParameter("class_id");
        StudentClassService studentClassService=new StudentClassService();
        StudentClass studentClass = studentClassService.searchClass(class_id);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchStudentClass.jsp");
            request.getSession().setAttribute("studentclass",studentClass);
            requestDispatcher.forward(request,response);
        }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
