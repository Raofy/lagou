package com.fuyi.student.service;


import com.fuyi.student.dao.userdao.UserDao;
import com.fuyi.student.factory.UserFactory;
import com.fuyi.student.model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = UserFactory.createUserDao();
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public User userLoginService(User user){
        return userDao.userLogin(user);
    }
}
