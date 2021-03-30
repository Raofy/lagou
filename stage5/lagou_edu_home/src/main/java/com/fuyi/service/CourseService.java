package com.fuyi.service;

import com.fuyi.pojo.Course;

import java.util.List;

public interface CourseService {

    public List<Course> findCourseList();

    public List<Course> findByCourseByNameAndStatus(String name, String status);

    public String courseSalesInfo(Course course);

    public String updateSalesInfo(Course course);

    public int updateCourseStatus(int courseId);

    public Course findCourseById(int courseId);
}
