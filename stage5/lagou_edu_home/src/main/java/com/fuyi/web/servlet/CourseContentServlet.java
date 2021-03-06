package com.fuyi.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.fuyi.base.BaseServlet;
import com.fuyi.base.StatusCode;
import com.fuyi.pojo.Course;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;
import com.fuyi.service.CourseContentService;
import com.fuyi.service.impl.CourseContentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "CourseContentServlet", urlPatterns = "/courseContent")
public class CourseContentServlet extends BaseServlet {

    public void findSectionAndLessonByCourseId(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取请求参数
            String courseId = request.getParameter("course_id");

            // 业务处理
            CourseContentService courseContentService = new CourseContentServiceImpl();
            List<Course_Section> sectionAndLessonByCourseId = courseContentService.findSectionAndLessonByCourseId(Integer.valueOf(courseId));

            // Json序列化和返回数据
            response.getWriter().print(JSON.toJSONString(sectionAndLessonByCourseId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取请求参数
            String courseId = request.getParameter("course_id");

            // 业务处理
            CourseContentService courseContentService = new CourseContentServiceImpl();
            Course courseInfo = courseContentService.findCourseById(Integer.valueOf(courseId));

            // 返回指定的列值
            SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(Course.class, "id", "course_name");

            // Json序列化
            String resultInfo = JSON.toJSONString(courseInfo, simplePropertyPreFilter);

            // 返回结果
            response.getWriter().print(resultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateSection(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取请求参数
            Map map = (Map) request.getAttribute("map");

            // 将Map转为实体类
            Course_Section courseSection = new Course_Section();
            BeanUtils.copyProperties(courseSection, map.get("section"));

            // 业务处理
            CourseContentService courseContentService = new CourseContentServiceImpl();
            int result = courseContentService.saveOrUpdateSection(courseSection);

            // 返回结果
            if (result > 0) {
                response.getWriter().print(StatusCode.SUCCESS.toString());
            } else {
                response.getWriter().print(StatusCode.FAIL.toString());
            }
        } catch (Exception e) {

        }
    }

    public void updateSectionStatus(HttpServletRequest request, HttpServletResponse response) {

        try {
            // 获取请求参数
            String id = request.getParameter("id");
            String status = request.getParameter("status");

            // 业务处理
            CourseContentService courseContentService = new CourseContentServiceImpl();
            int result = courseContentService.updateSectionStatus(Integer.valueOf(id), Integer.valueOf(status));

            // 返回结果
            if (result > 0) {
                response.getWriter().print(StatusCode.SUCCESS.toString());
            } else {
                response.getWriter().print(StatusCode.FAIL.toString());
            }
        } catch (Exception e) {

        }
    }

    public void saveOrUpdateLesson(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取请求参数
            HashMap<String, Object> map = (HashMap<String, Object>) request.getAttribute("map");

            // 将Map转为实体类
            Course_Lesson courseLesson = new Course_Lesson();
            BeanUtils.copyProperties(courseLesson, (Map) map.get("lesson"));

            // 业务处理
            CourseContentService courseContentService = new CourseContentServiceImpl();
            int result = courseContentService.saveOrUpdateLesson(courseLesson);

            // 返回结果
            if (result > 0) {
                response.getWriter().print(StatusCode.SUCCESS.toString());
            } else {
                response.getWriter().print(StatusCode.FAIL.toString());
            }
        } catch (Exception e) {

        }
    }

    //根据章节ID 回显章节信息
    public void findSectionBySectionId(HttpServletRequest request,HttpServletResponse response){

        try {
            //获取参数
            int section_id = Integer.parseInt(request.getParameter("section_id"));

            //业务处理
            CourseContentService contentService = new CourseContentServiceImpl();
            Course_Section section = contentService.findSectionBySectionId(section_id);

            //返回JSON数据
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course_Section.class,"id","section_name");
            String result = JSON.toJSONString(section, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
