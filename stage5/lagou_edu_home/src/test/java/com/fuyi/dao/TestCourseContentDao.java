package com.fuyi.dao;

import com.fuyi.dao.impl.CourseContentDaoImpl;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;
import com.fuyi.utils.DateUtils;
import org.junit.Test;


public class TestCourseContentDao {

    CourseContentDao courseContentDao = new CourseContentDaoImpl();

    @Test
    public void testFindSectionAndLessonByCourseId() {
        courseContentDao.findSectionAndLessonByCourseId(46).forEach(System.out::print);
    }

    /**
     * 测试回显章节对应的课程信息回显
     */
    @Test
    public void testFindCourseByCourseId() {
        System.out.println(courseContentDao.findCourseById(20));
    }

    /**
     * 测试保存章节信息
     */
    @Test
    public void testSaveSection() {
        Course_Section courseSection = new Course_Section();
        courseSection.setCourse_id(19);
        courseSection.setSection_name("微服务架构进阶篇");
        courseSection.setDescription("跟着药水一起学习如何使用微服务");
        courseSection.setOrder_num(0);
        courseSection.setStatus(1);
        courseSection.setCreate_time(DateUtils.getDateFormat());
        courseSection.setUpdate_time(DateUtils.getDateFormat());
        int i = courseContentDao.saveSection(courseSection);
        System.out.println(i);
    }

    /**
     * 测试更新章节信息
     */
    @Test
    public void testUpdateSection() {
        Course_Section courseSection = new Course_Section();
        courseSection.setCourse_id(19);
        courseSection.setId(44);
        courseSection.setSection_name("微服务架构进阶篇");
        courseSection.setDescription("跟着平头哥一起学习如何使用微服务");
        courseSection.setOrder_num(0);
        courseSection.setStatus(1);
        courseSection.setUpdate_time(DateUtils.getDateFormat());
        int i = courseContentDao.updateSection(courseSection);
        System.out.println(i);
    }

    /**
     * 修改章节状态
     */
    @Test
    public void testUpdateSectionStatus() {
        int sessionId = 43;
        int status = 0;
        System.out.println(courseContentDao.updateSectionStatus(sessionId, status));
    }

    /**
     * 保存课时信息
     */
    @Test
    public void testSaveCourseLesson() {
        Course_Lesson courseLesson = new Course_Lesson();
        courseLesson.setCourse_id(1);
        courseLesson.setSection_id(1);
        courseLesson.setTheme("主题一");
        courseLesson.setDuration(10);
        courseLesson.setIs_free(0);
        courseLesson.setOrder_num(1);
        courseLesson.setCreate_time(DateUtils.getDateFormat());
        courseLesson.setUpdate_time(DateUtils.getDateFormat());
        System.out.println(courseContentDao.saveLesson(courseLesson));
    }

    /**
     * 更新课时信息
     */
    @Test
    public void testUpdateCourseLesson() {
        Course_Lesson courseLesson = new Course_Lesson();
        courseLesson.setId(43);
        courseLesson.setCourse_id(1);
        courseLesson.setSection_id(1);
        courseLesson.setTheme("主题1  ");
        courseLesson.setDuration(10);
        courseLesson.setIs_free(0);
        courseLesson.setOrder_num(1);
        courseLesson.setCreate_time(DateUtils.getDateFormat());
        courseLesson.setUpdate_time(DateUtils.getDateFormat());
        System.out.println(courseContentDao.updateLesson(courseLesson));
    }
}
