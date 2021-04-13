package com.fuyi.dao.impl;

import com.fuyi.dao.CourseContentDao;
import com.fuyi.pojo.Course;
import com.fuyi.pojo.Course_Lesson;
import com.fuyi.pojo.Course_Section;
import com.fuyi.utils.DateUtils;
import com.fuyi.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseContentDaoImpl implements CourseContentDao {
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {

        try {
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT \n" +
                     "id,\n" +
                     "course_id,\n" +
                     "section_name,\n" +
                     "description,\n" +
                     "order_num,\n" +
                     "STATUS\n" +
                     "FROM course_section WHERE course_id = ?";

            List<Course_Section> query = queryRunner.query(sql, new BeanListHandler<>(Course_Section.class), courseId);
            if (query != null) {
                for (Course_Section course_section : query) {
                    List<Course_Lesson> lessonBySectionId = findLessonBySectionId(course_section.getId());
                    if (lessonBySectionId != null) {
                        course_section.setCourse_lessons(lessonBySectionId);
                    } else {
                        course_section.setCourse_lessons(new ArrayList<>());
                    }
                }
                return query;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Course_Lesson> findLessonBySectionId(int sectionId) {
        try {
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT \n" +
                         "id,\n" +
                         "course_id,\n" +
                         "section_id,\n" +
                         "theme,\n" +
                         "duration,\n" +
                         "is_free,\n" +
                         "order_num,\n" +
                         "STATUS\n" +
                         "FROM course_lesson WHERE section_id = ?";

            List<Course_Lesson> query = queryRunner.query(sql, new BeanListHandler<>(Course_Lesson.class), sectionId);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Course findCourseById(int courseId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "SELECT id,course_name FROM course WHERE id = ?";

            Course course = qr.query(sql, new BeanHandler<Course>(Course.class), courseId);

            return course;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int saveSection(Course_Section section) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "INSERT INTO course_section(\n" +
                         "course_id,\n" +
                         "section_name,\n" +
                         "description,\n" +
                         "order_num,\n" +
                         "STATUS,\n" +
                         "create_time,\n" +
                         "update_time)VALUES(?,?,?,?,?,?,?);";

            Object[] param = {section.getCourse_id(),section.getSection_name(),section.getDescription(),
                    section.getOrder_num(),section.getStatus(),section.getCreate_time(),section.getUpdate_time()};

            int row = qr.update(sql, param);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateSection(Course_Section section) {

        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course_section SET\n" +
                         "section_name = ?,\n" +
                         "description = ?,\n" +
                         "order_num = ?,\n" +
                         "update_time = ? WHERE id = ?";

            Object[] param = {section.getSection_name(),section.getDescription(),section.getOrder_num(),
                    section.getUpdate_time(),section.getId()};

            int row = qr.update(sql, param);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateSectionStatus(int sessionId, int status) {
        try {
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_section set status = ?, update_time = ? where id = ?;";
            Object[] param = {status, DateUtils.getDateFormat(), sessionId};
            int update = queryRunner.update(sql, param);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int saveLesson(Course_Lesson lesson) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "insert into course_lesson (" +
                    "course_id, " +
                    "section_id, " +
                    "theme, " +
                    "duration, " +
                    "is_free, " +
                    "order_num, " +
                    "create_time, update_time) " +
                    "value(?, ?, ? , ? , ? , ? , ? , ?);";

            Object[] param = {lesson.getCourse_id(), lesson.getSection_id(), lesson.getTheme(), lesson.getDuration(),
            lesson.getIs_free(), lesson.getOrderNum(), lesson.getCreate_time(), lesson.getUpdate_time()};

            int row = qr.update(sql, param);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateLesson(Course_Lesson lesson) {

        try {
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_lesson set course_id = ?, section_id = ?, theme = ?, duration = ?, is_free = ?, order_num = ?, update_time = ? where id = ?;";
            Object[] param = {lesson.getCourse_id(), lesson.getSection_id(), lesson.getTheme(), lesson.getDuration(),
            lesson.getIs_free(), lesson.getOrderNum(), lesson.getUpdate_time(), lesson.getId()};
            int update = queryRunner.update(sql, param);
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
