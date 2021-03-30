package com.fuyi.student.servlet.StudentServlet;


import com.fuyi.student.model.Student;
import com.fuyi.student.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SearchStudentServlet",urlPatterns = "/searchStudentServlet")
public class SearchStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        //获取输入的学号
        String stu_id = request.getParameter("stu_id");
        //打印参数的值
        System.out.println(stu_id);
        //创建studentService对象调用方法
        StudentService studentService=new StudentService();
            Student student = studentService.checkStudent(stu_id);
            //将学生对象的信息传递到jsp页面中
            System.out.println(student);
            //将student对象发送到student.jsp 中的查询的模态框
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("searchStudentPage.jsp");
            request.getSession().setAttribute("student",student);
            //转发回前台页面
            requestDispatcher.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
