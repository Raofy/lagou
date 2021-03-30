package com.fuyi.student.factory;


import com.fuyi.student.dao.userdao.UserDao;
import com.fuyi.student.dao.userdao.UserDaoImp;

//工厂类
public class UserFactory {

    //返回userdao接口的实现类的对象
    public static UserDao createUserDao() {
        return new UserDaoImp();
    }
}
