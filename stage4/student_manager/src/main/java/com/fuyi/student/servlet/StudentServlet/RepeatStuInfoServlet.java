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

@WebServlet(name = "RepeatStuInfoServlet" ,urlPatterns = "/repeatStuInfoServlet")
public class RepeatStuInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        PrintWriter writer = response.getWriter();
        String stu_id_before = request.getParameter("stu_id_before");
        System.out.println(stu_id_before);
        String stu_id_later = request.getParameter("stu_id_later");
        String stu_name = request.getParameter("stu_name");
        String stu_sex = request.getParameter("stu_sex");
        String stu_birthday = request.getParameter("stu_birthday");
        String stu_email = request.getParameter("stu_email");
        String stu_class = request.getParameter("stu_class");
        String stu_comment = request.getParameter("stu_comment");
        StudentService studentService=new StudentService();
        Student student = studentService.checkStudent(stu_id_before);
        //如果提交的id在数据库中没有存在  那么就可以实现修改  存在的话就提示 id已经存在
        if (stu_id_later.equals(student.getStu_id())){
            writer.println("该id已经存在了,请重新设置id");
        }else {

            //进行修改的操作
            int i = studentService.repeatStudent(stu_id_before, new Student(stu_id_later, stu_name, stu_sex, stu_birthday, stu_email, stu_class,stu_comment));
            if (i>0){
                writer.println("修改成功");
            }else if ("".equals(stu_id_later)){
                writer.println("请输入修改后的id");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
