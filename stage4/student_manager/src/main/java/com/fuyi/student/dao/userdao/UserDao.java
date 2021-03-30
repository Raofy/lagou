package com.fuyi.student.dao.userdao;


import com.fuyi.student.model.User;

public interface UserDao {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public abstract User userLogin(User user);

}
