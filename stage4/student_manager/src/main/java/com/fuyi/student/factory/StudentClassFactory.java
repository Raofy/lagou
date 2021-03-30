package com.fuyi.student.factory;


import com.fuyi.student.dao.classdao.StudentClassDao;
import com.fuyi.student.dao.classdao.StudentClassImp;

/*
 * 工厂类  返回StudentClassDao的实现类的对象
 * */
public class StudentClassFactory {

    public static StudentClassDao createStudentClassDao(){
        return new StudentClassImp();
    }
}
