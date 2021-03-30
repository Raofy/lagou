package com.fuyi.student.servlet.StudentClassServlet;

import com.fuyi.student.model.Page;
import com.fuyi.student.model.StudentClass;
import com.fuyi.student.service.StudentClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentClassPageServlet", urlPatterns = {"/findStudentClassByPageServlet"})
public class StudentClassPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取请求的路径
        StringBuffer requestURL = request.getRequestURL();
        //将请求路径切割 得到对应的urlPatterns
        String action = requestURL.substring(requestURL.lastIndexOf("/") + 1);
        System.out.println(requestURL);
        Object user = request.getSession().getAttribute("main");
        if (action.equals("findStudentClassByPageServlet")&&user!=null){
            //获取传递过来的值
            String  currentPage = request.getParameter("currentPage");//当前的页码
            String  rows = request.getParameter("rows");//每页显示的条数
            if (currentPage==null||rows==null){
                 currentPage="1";
                 rows="5";
            }
            //创建服务
            StudentClassService studentClassService=new StudentClassService();
            //调用方法查询得到指定的一页的数据
            Page<StudentClass> page = studentClassService.findStudentClassByPage(currentPage, rows);
            //防拦截
            request.getSession().setAttribute("Page", "StudentClassPage");
            //转发到前台
            request.getSession().setAttribute("page",page);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("class.jsp");

            requestDispatcher.forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }
}
