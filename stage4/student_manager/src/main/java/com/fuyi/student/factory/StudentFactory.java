package com.fuyi.student.factory;


import com.fuyi.student.dao.studentdao.StudentDao;
import com.fuyi.student.dao.studentdao.StudentDaoImp;

/*
* 工厂类  StudentDao实现类的对象
* */
public class StudentFactory {

    public static StudentDao createStudentDao() {
        return new StudentDaoImp();
    }
}
