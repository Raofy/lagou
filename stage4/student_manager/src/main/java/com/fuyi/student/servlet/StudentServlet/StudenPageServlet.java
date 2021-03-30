package com.fuyi.student.servlet.StudentServlet;


import com.fuyi.student.model.Page;
import com.fuyi.student.model.Student;
import com.fuyi.student.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentPageServlet", urlPatterns ={"/findStudentByPageServlet"} )
public class StudenPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        StringBuffer requestURL = request.getRequestURL();
        //将请求路径切割 得到对应的url
        String action= requestURL.substring(requestURL.lastIndexOf("/") + 1);
        Object user = request.getSession().getAttribute("main");
        System.out.println(user);
        //做一个访问的判断  如果没有访问到主页那就无法访问此页
        if (action.equals("findStudentByPageServlet")&&user!=null){
            //获取传递过来的值
            String  currentPage = request.getParameter("currentPage");//当前的页码
            String  rows = request.getParameter("rows");//每页显示的条数
            if (currentPage==null||rows==null){
                currentPage="1";
                rows="5";
            }
            //创建服务
            StudentService studentService=new StudentService();
            //调用方法查询得到指定的一页的数据
            Page<Student> page = studentService.findStudentByPage(currentPage, rows);
            //防拦截
            request.getSession().setAttribute("PageFilter","StudentPage");
            System.out.println(page);
            //转发到前台
            request.getSession().setAttribute("StudentPage",page);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("student.jsp");
            requestDispatcher.forward(request,response);
        }else {
            response.sendRedirect("login.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      this.doPost(request, response);
    }
}
