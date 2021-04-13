package com.fuyi.service;

import com.fuyi.pojo.Course;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;

import java.util.List;

public interface CourseContentService {


    public List<Course_Section> findSectionAndLessonByCourseId (int courseId);

    public List<Course_Lesson> findLessonBySectionId(int sessionId);

    public Course findCourseById(int courseId);

    public int saveOrUpdateSection(Course_Section courseSection);

    public int updateSectionStatus(int sessionId, int status);

    public int saveOrUpdateLesson(Course_Lesson lesson);

}
