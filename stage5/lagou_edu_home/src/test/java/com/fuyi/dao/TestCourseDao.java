package com.fuyi.dao;

import com.fuyi.dao.impl.CourseDaoImpl;
import com.fuyi.pojo.Course;
import com.fuyi.service.impl.CourseServiceImpl;
import com.fuyi.utils.DateUtils;
import org.junit.Test;

public class TestCourseDao {

    CourseDao dao = new CourseDaoImpl();

    @Test
    public void testDao() {
        dao.findCourseList().forEach(System.out::println);
    }

    @Test
    public void testFindCourseByNameAndStatus() {
        dao.findCourseByNameAndStatus("Java", "1").forEach(System.out::println);
    }

    @Test
    public void testCourseSalesInfo() {
        //1.创建course对象
        Course course = new Course();
        course.setCourse_name("爱情36计");
        course.setBrief("学会去找对象");
        course.setTeacher_name("药水哥");
        course.setTeacher_info("人人都是药水哥");
        course.setPreview_first_field("共10讲");
        course.setPreview_second_field("每周日更新");
        course.setDiscounts(88.88);
        course.setPrice(188.0);
        course.setPrice_tag("最新优惠价");
        course.setShare_image_title("哈哈哈");
        course.setShare_title("嘻嘻嘻");
        course.setShare_description("天天向上");
        course.setCourse_description("爱情36计,就像一场游戏");
        course.setCourse_img_url("https://www.xx.com/xxx.jpg");
        course.setStatus(1); //1 上架 ,0 下架
        String formart = DateUtils.getDateFormat();
        course.setCreate_time(formart);
        course.setUpdate_time(formart);

        String result = dao.courseSalesInfo(course);
        System.out.println(result);
    }

    @Test
    public void testUpdateCourseStatus() {
        int status = 0;
        int courseId = 1;
        System.out.println(dao.updateCourseStatus(courseId, status));
    }
}
