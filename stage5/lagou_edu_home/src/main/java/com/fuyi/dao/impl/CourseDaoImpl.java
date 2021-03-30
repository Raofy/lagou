package com.fuyi.dao.impl;

import com.fuyi.base.StatusCode;
import com.fuyi.dao.CourseDao;
import com.fuyi.pojo.Course;
import com.fuyi.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 课程类Dao层
 */
public class CourseDaoImpl implements CourseDao {

    @Override
    public List<Course> findCourseList() {
        try {
            // 1. 创建QueryRunner
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

            // 2. 编写sql语句
            String sql = "SELECT id, course_name, price, sort_num, status FROM course WHERE is_del = ?";

            // 3. 执行查询
            List<Course> queryResult = queryRunner.query(sql, new BeanListHandler<Course>(Course.class), 0);

            // 4. 返回结果
            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Course> findCourseByNameAndStatus(String name, String status) {

        try {
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

            // 2. 创建一个StringBuffer进行拼接SQL语句
            StringBuffer sql = new StringBuffer("SELECT id, course_name, price, sort_num, status FROM course WHERE is_del = ? ");

            // 3. 使用一个数组进行存储参数值
            List<Object> params = new ArrayList<>();
            params.add(0);
            if (null != name && "" != name) {
                sql.append("AND course_name like ? ");
                name = "%" + name + "%";
                params.add(name);
            }
            if (null != status && "" != status) {
                sql.append("AND status = ?;");
                params.add(status);
            }
            return queryRunner.query(sql.toString(), new BeanListHandler<Course>(Course.class), params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String courseSalesInfo(Course course) {
        try {
            //1.创建QueryRunner
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            //2.编写SQL
            String sql = "INSERT INTO course(\n" +
                    "course_name,\n" +
                    "brief,\n" +
                    "teacher_name,\n" +
                    "teacher_info,\n" +
                    "preview_first_field,\n" +
                    "preview_second_field,\n" +
                    "discounts,\n" +
                    "price,\n" +
                    "price_tag,\n" +
                    "share_image_title,\n" +
                    "share_title,\n" +
                    "share_description,\n" +
                    "course_description,\n" +
                    "course_img_url,\n" +
                    "STATUS,\n" +
                    "create_time,\n" +
                    "update_time\n" +
                    ")VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            //3.准备参数
            Object[] param = {
                    course.getCourse_name(),
                    course.getBrief(),
                    course.getTeacher_name(),
                    course.getTeacher_info(),
                    course.getPreview_first_field(),
                    course.getPreview_second_field(),
                    course.getDiscounts(),
                    course.getPrice(),
                    course.getPrice_tag(),
                    course.getShare_image_title(),
                    course.getShare_title(),
                    course.getShare_description(),
                    course.getCourse_description(),
                    course.getCourse_img_url(),
                    course.getStatus(),
                    course.getCreate_time(),
                    course.getUpdate_time()
            };

            //4.执行插入操作
            int row = qr.update(sql, param);

            if (row > 0) {
                return StatusCode.SUCCESS.toString();
            } else {
                return StatusCode.FAIL.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public String updateCourseSalesInfo(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());

            String sql = "UPDATE course SET \n" +
                    "course_name = ?,\n" +
                    "brief = ?,\n" +
                    "teacher_name = ?,\n" +
                    "teacher_info = ?,\n" +
                    "preview_first_field = ?,\n" +
                    "preview_second_field = ?,\n" +
                    "discounts = ?,\n" +
                    "price = ?,\n" +
                    "price_tag = ?,\n" +
                    "share_image_title = ?,\n" +
                    "share_title = ?,\n" +
                    "share_description = ?,\n" +
                    "course_description = ?,\n" +
                    "course_img_url = ?,\n" +
                    "update_time = ? \n" +
                    "WHERE id = ?";

            Object[] param = {course.getCourse_name(), course.getBrief(), course.getTeacher_name(), course.getTeacher_info(),
                    course.getPreview_first_field(), course.getPreview_second_field(), course.getDiscounts(), course.getPrice(),
                    course.getPrice_tag(), course.getShare_image_title(), course.getShare_title(), course.getShare_description(), course.getCourse_description(),
                    course.getCourse_img_url(), course.getUpdate_time(), course.getId()};

            int row = qr.update(sql, param);

            if (row > 0) {
                return StatusCode.SUCCESS.toString();
            } else {
                return StatusCode.FAIL.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return StatusCode.FAIL.toString();
        }

    }

    @Override
    public int updateCourseStatus(int courseId, int status) {
        try {
            // 1. 创建QueryRunner
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

            // 2. 编写sql语句
            String sql = "update course set status = ? where id = ?";

            Object[] param = {status, courseId};

            // 3. 执行查询并返回结果
            return queryRunner.update(sql, param);

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Course findCourseById(int courseId) {
        try {
            // 1. 创建QueryRunner
            QueryRunner queryRunner = new QueryRunner(DruidUtils.getDataSource());

            // 2. 编写sql语句
            String sql = "";

            // 3. 查询数据库并返回结果
            return queryRunner.query(sql, new BeanHandler<>(Course.class), courseId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
