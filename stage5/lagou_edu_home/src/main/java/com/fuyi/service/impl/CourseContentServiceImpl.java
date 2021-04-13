package com.fuyi.service.impl;

import com.fuyi.dao.CourseContentDao;
import com.fuyi.dao.impl.CourseContentDaoImpl;
import com.fuyi.pojo.Course;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;
import com.fuyi.service.CourseContentService;
import com.fuyi.utils.DateUtils;

import java.util.List;

public class CourseContentServiceImpl implements CourseContentService {

    CourseContentDao courseContentDao = new CourseContentDaoImpl();

    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {
        return courseContentDao.findSectionAndLessonByCourseId(courseId);
    }

    @Override
    public List<Course_Lesson> findLessonBySectionId(int sessionId) {
        return courseContentDao.findLessonBySectionId(sessionId);
    }

    @Override
    public Course findCourseById(int courseId) {
        return courseContentDao.findCourseById(courseId);
    }

    @Override
    public int saveOrUpdateSection(Course_Section courseSection) {
        if (courseSection.getId() == 0) {                              // 添加操作
            courseSection.setOrder_num(0);
            courseSection.setStatus(1);
            courseSection.setCreate_time(DateUtils.getDateFormat());
            courseSection.setUpdate_time(DateUtils.getDateFormat());
            int result = courseContentDao.saveSection(courseSection);
            return result;
        } else {                                                       //更新操作
            courseSection.setOrder_num(0);
            courseSection.setStatus(1);
            courseSection.setUpdate_time(DateUtils.getDateFormat());
            int result = courseContentDao.updateSection(courseSection);
            return result;
        }
    }

    @Override
    public int updateSectionStatus(int sessionId, int status) {
        return courseContentDao.updateSectionStatus(sessionId, status);
    }

    @Override
    public int saveOrUpdateLesson(Course_Lesson lesson) {
        if (lesson.getId() == 0) {                                     // 添加操作
            lesson.setCreate_time(DateUtils.getDateFormat());
            lesson.setUpdate_time(DateUtils.getDateFormat());
            int result = courseContentDao.saveLesson(lesson);
            return result;
        } else {                                                       //更新操作
            lesson.setUpdate_time(DateUtils.getDateFormat());
            int result = courseContentDao.updateLesson(lesson);
            return result;
        }
    }
}
