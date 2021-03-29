package com.fuyi.dao;

import com.alibaba.fastjson.annotation.JSONField;
import com.fuyi.pojo.Course;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程类Dao层
 * */
public interface CourseDao {

  /**
   * 获取课程列表信息
   *
   * @return
   */
  public List<Course> findCourseList();

  /**
   * 多条件查询课程列表
   *
   * @param name
   * @param status
   * @return
   */
  public List<Course> findCourseByNameAndStatus(String name, String status);

  /**
   * 保存课程信息
   *
   * @param course
   * @return
   */
  public String courseSalesInfo(Course course);

  public String updateCourseSalesInfo(Course course);

  public int updateCourseStatus(int courseId, int status);
}
