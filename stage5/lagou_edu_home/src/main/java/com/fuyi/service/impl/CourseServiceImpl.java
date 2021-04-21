package com.fuyi.service.impl;

import com.fuyi.dao.CourseDao;
import com.fuyi.dao.impl.CourseDaoImpl;
import com.fuyi.pojo.Course;
import com.fuyi.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {

    //创建CourseDao
    CourseDao courseDao = new CourseDaoImpl();

    public List<Course> findCourseList() {
        return courseDao.findCourseList();
    }

    @Override
    public List<Course> findByCourseByNameAndStatus(String name, String status) {
        return courseDao.findCourseByNameAndStatus(name, status);
    }

    @Override
    public String courseSalesInfo(Course course) {
        return courseDao.courseSalesInfo(course);
    }

    @Override
    public String updateSalesInfo(Course course) {
        return courseDao.updateCourseSalesInfo(course);
    }

    @Override
    public int updateCourseStatus(int courseId) {
        Course course = findCourseById(courseId);
        if (course.getStatus() == 0) {
            return courseDao.updateCourseStatus(courseId, 1);
        } else {
            return courseDao.updateCourseStatus(courseId, 0);
        }
    }

    @Override
    public Course findCourseById(int courseId) {
        return courseDao.findCourseById(courseId);
    }
}
