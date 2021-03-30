package com.fuyi.student.servlet.StudentClassServlet;


import com.fuyi.student.service.StudentClassService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DeleteStudentClassServlet",urlPatterns = "/deleteStudentClassServlet")
public class DeleteStudentClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //获取响应对象的write方法
        PrintWriter writer = response.getWriter();
        //获取前台发送来的数据(要删除的班级的编号)
        String classname = request.getParameter("classname");
        //将发送来的数据进行分割
        String[] class_ids = classname.split(",");
        StudentClassService studentClassService=new StudentClassService();
        List<String> list = studentClassService.delClass(class_ids);
        if (classname!=""){
            //将没有删除的数据写回前台
            String info=null;
            for (String data:list ){
                info=data+" ";
            }
            if (info==null){
                writer.println("删除成功");
            }else {
                writer.println("班级"+info+"不为空哦，不可以删除哦");
            }
        }else {
            writer.println("请选则你要删除的班级");
        }

        }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
