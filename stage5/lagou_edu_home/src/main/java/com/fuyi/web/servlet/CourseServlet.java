package com.fuyi.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.fuyi.base.BaseServlet;
import com.fuyi.pojo.Course;
import com.fuyi.service.CourseService;
import com.fuyi.service.impl.CourseServiceImpl;
import com.fuyi.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CourseServlet", urlPatterns = "/course")
public class CourseServlet extends BaseServlet {

    public void findCourseList(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 1. 接收参数，在BaseServlet已经完成了

            // 2. 业务处理
            CourseService courseService = new CourseServiceImpl();
            List<Course> courseList = courseService.findCourseList();

            // 3. 指定返回的字段
            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(Course.class, "id", "course_name", "price", "sort_num", "status");
            // 4. 返回结果
            response.getWriter().write(JSON.toJSONString(courseList, simplePropertyPreFilter));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 1. 接收参数，在BaseServlet已经完成了
            String courseName = request.getParameter("course_name");
            String status = request.getParameter("status");
            // 2. 业务处理
            CourseService courseService = new CourseServiceImpl();
            List<Course> courseList = courseService.findByCourseByNameAndStatus(courseName, status);

            // 3. 指定返回的字段
            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(Course.class, "id", "course_name", "price", "sort_num", "status");
            // 4. 返回结果
            response.getWriter().write(JSON.toJSONString(courseList, simplePropertyPreFilter));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void courseSalesInfo(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 1. 获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();

            // 2. 将Map转为实体类
            Course course = new Course();
            BeanUtils.populate(course, parameterMap);

            // 3. 业务处理类
            CourseServiceImpl courseService = new CourseServiceImpl();
            String result = courseService.courseSalesInfo(course);

            // 4. 返回结果
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCourseStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 接收参数，在BaseServlet已经完成了
            String id = request.getParameter("id");
            // 2. 业务处理
            CourseService courseService = new CourseServiceImpl();
            int result = courseService.updateCourseStatus(Integer.valueOf(id));
            // 3. 返回结果
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. 接收参数，在BaseServlet已经完成了
            String id = request.getParameter("id");

            // 2. 业务处理
            CourseService courseService = new CourseServiceImpl();
            Course result = courseService.findCourseById(Integer.valueOf(id));

            // 3. 返回结果
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
