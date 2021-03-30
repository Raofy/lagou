package com.fuyi.student.servlet.StudentClassServlet;

import com.fuyi.student.model.StudentClass;
import com.fuyi.student.service.StudentClassService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AddStudentClassServlet", urlPatterns = "/addStudentClassServlet")
public class AddStudentClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        PrintWriter writer = response.getWriter();

        String class_id = request.getParameter("class_id");
        String class_name = request.getParameter("class_name");
        String grade_name = request.getParameter("grade_name");
        String class_teacher = request.getParameter("class_teacher");
        String class_slogan = request.getParameter("class_slogan");

        StudentClassService studentClassService=new StudentClassService();
       //调用添加的服务  返回发生改变的行数
        int i = studentClassService.addClass(new StudentClass( grade_name,  class_id,  class_name,  class_slogan,  class_teacher));
        //如果i>0那么说明添加成功
        if (i>0){
            writer.println("添加成功");
        }else {
            writer.println("添加失败,该班级编号已经存在");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
