package com.fuyi.test;

import com.fuyi.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MyTest {

    @Test
    public void queryTest() throws IOException {

        // 1. 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2. 获取SQLFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3. 获取SQLSession对象
        SqlSession sqlSession = factory.openSession();

        // 4. 执行SQL参数
        List<User> result = sqlSession.selectList("UserMapper.findAll");

        // 5. 打印结果
        result.forEach(System.out::println);

        // 6. 释放资源
        sqlSession.close();
    }

    @Test
    public void saveTest() throws IOException {
        // 1. 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2. 获取SQLFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3. 获取SQLSession对象
        SqlSession sqlSession = factory.openSession();

        // 4. 创建user实体对象
        User user = new User();
        user.setUsername("Lucy");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("广东广州");

        // 5. 执行SQL参数
        int insert = sqlSession.insert("UserMapper.save", user);
        System.out.println(insert);

        // 6. 提交事务
        sqlSession.commit();

        // 7. 释放资源
        sqlSession.close();
    }

    @Test
    public void updateTest() throws IOException {
        // 1. 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2. 获取SQLFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3. 获取SQLSession对象
        SqlSession sqlSession = factory.openSession();

        // 4. 创建user实体对象
        User user = new User();
        user.setId(5);
        user.setUsername("Lucy");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("广东肇庆");

        // 5. 执行SQL参数
        int update = sqlSession.update("UserMapper.updateById", user);
        System.out.println(update);

        // 6. 提交事务
        sqlSession.commit();

        // 7. 释放资源
        sqlSession.close();
    }

    @Test
    public void deleteTest() throws IOException {
        // 1. 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 2. 获取SQLFactory工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3. 获取SQLSession对象
        SqlSession sqlSession = factory.openSession();

        // 4. 执行SQL参数
        int delete = sqlSession.delete("UserMapper.deleteById", 5);
        System.out.println(delete);

        // 5. 提交事务
        sqlSession.commit();

        // 6. 释放资源
        sqlSession.close();


    }
}
