package com.fuyi.dao;

import com.fuyi.pojo.Course;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;

import java.util.List;

/**
 * 课程章节Dao层
 */
public interface CourseContentDao {

    /**
     * 根据课程ID查询课程相关章节信息
     *
     * @param courseId
     * @return
     */
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId);

    /**
     * 根据章节ID 查询章节相关的课时信息
     *
     * @param sectionId
     * @return
     */
    public List<Course_Lesson> findLessonBySectionId(int sectionId);

    /**
     * 回显章节对应的课程信息
     *
     * @param courseId
     * @return
     */
    public Course findCourseById(int courseId);

    /**
     * 保存章节信息
     *
     * @param courseSection
     * @return
     */
    public int saveSection(Course_Section courseSection);

    /**
     * 修改章节信息
     *
     * @param courseSection
     * @return
     */
    public int updateSection(Course_Section courseSection);


    /**
     * 修改章节状态
     * @param sessionId
     * @param status
     * @return
     */
    public int updateSectionStatus(int sessionId, int status);

    /**
     * 添加课时信息
     *
     * @param lesson
     * @return
     */
    public int saveLesson(Course_Lesson lesson);

    /**
     * 添加课时信息
     *
     * @param lesson
     * @return
     */
    public int updateLesson(Course_Lesson lesson);
}
