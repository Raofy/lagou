package com.fuyi.util;

import com.fuyi.pojo.Course;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    @Test
    public void testBeanUtil() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        // 1. 将Map集合转为Course实体类

        // 2. 创建Course
        Course course = new Course();

        // 3. 向Map中添加数据，key和实体类Course的属性名要一致，value类型要和Coure的类型一致
        Map<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("course_name","大数据");
        map.put("brief","课程包含所有大数据流行的技术");
        map.put("teacher_name","周星星");
        map.put("teacher_info","非著名演员");

        //将map中的数据封装到 course中
        BeanUtils.populate(course,map);

        System.out.println(course.getId()+" " + course.getCourse_name() +" " +course.getBrief()
                +"   "+course.getTeacher_name()+" " +course.getTeacher_info());

        //设置属性 获取属性
        BeanUtils.setProperty(course,"price",100.0);

        String price = BeanUtils.getProperty(course, "price");

        System.out.println(price);


    }
}
