package com.fuyi.student.servlet.StudentServlet;

import com.fuyi.student.model.Student;
import com.fuyi.student.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddStudentServlet", urlPatterns = "/addStudentServlet")
public class AddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        PrintWriter writer = response.getWriter();
        String stu_id = request.getParameter("stu_id");
        String stu_name = request.getParameter("stu_name");
        String stu_sex = request.getParameter("stu_sex");
        String stu_birthday = request.getParameter("stu_birthday");
        String stu_email = request.getParameter("stu_email");
        String stu_class = request.getParameter("stu_class");
        String stu_comment = request.getParameter("stu_comment");
        StudentService studentService = new StudentService();
        //先查看是否存在该学生的id
        Student student = studentService.checkStudent(stu_id);
        if (null == student) {
            int rows = studentService.addStudnet(new Student(stu_id, stu_name, stu_sex, stu_birthday, stu_email, stu_class,stu_comment));
            if (rows > 0) {
                //服务器跳转
                System.out.println("添加成功");
                writer.println("添加成功");

            } else {
                System.out.println("添加失败");
                writer.println("添加失败，请输入正确的信息");
                writer.flush();
            }
        } else {
            writer.println("该学号已经存在请勿重复添加");
            writer.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
