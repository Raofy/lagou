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

@WebServlet(name = "RepeatStudentClassServlet", urlPatterns = "/repeatStudentClassServlet")
public class RepeatStudentClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        PrintWriter writer = response.getWriter();
        //获取参数多对应的值
        String class_id_before = request.getParameter("class_id_before");
        String class_id_later = request.getParameter("class_id_later");
        String grade_name = request.getParameter("grade_name");
        String class_name = request.getParameter("class_name");
        String class_teacher = request.getParameter("class_teacher");
        String class_slogan = request.getParameter("class_slogan");
        StudentClassService studentClassService = new StudentClassService();
        //通过收到的class_id_later获取studentClass对象 判断要修改的班级编号是否存在
        StudentClass studentClass = studentClassService.checkClass(class_id_later);
        if (class_id_before != ""&&studentClass==null) {
            //进行修改的操作
            int i = studentClassService.repeatClass(new StudentClass(grade_name, class_id_later, class_name, class_slogan, class_teacher), class_id_before);
            if (i > 0) {
                writer.println("修改成功");
            } else {
                writer.println("修改失败,请输入正确的编号");
            }
        }else if (class_id_before==""){
            writer.println("请输入要修改的班级信息");
        }else if (studentClass!=null){
            writer.println("该编号已经存在 ,请重新输入");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
