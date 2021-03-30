package com.fuyi.student.dao.userdao;



import com.fuyi.student.model.User;
import com.fuyi.student.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImp implements UserDao {

    @Override
    public User userLogin(User user) {
        //创建QueryRunner的对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        //创建sql语句查询表中是否存在该用户
        String sql = "select *from `lagou_user` where name=? and password=?";
        User   resultuser=new User();
        try {
            resultuser = qr.query(sql, new BeanHandler<User>(User.class), user.getName(),user.getPassword());
            if (resultuser==null){
                return  null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultuser;
    }
}


