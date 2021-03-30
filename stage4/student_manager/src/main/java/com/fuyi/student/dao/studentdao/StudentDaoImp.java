package com.fuyi.student.dao.studentdao;

import com.fuyi.student.model.Student;
import com.fuyi.student.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;


public class StudentDaoImp implements StudentDao {
    //添加学生
   static QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
    @Override
    public int addStudent(Student student) {
        String sql = "insert into `lagou_student` (`stu_id`,`name`,`sex`,`birthday`,`email`,`comment`,`class_id`)values(?,?,?,?,?,?,?)";
        Object[] obj = new Object[]{student.getStu_id(), student.getName(), student.getSex(), student.getBirthday(), student.getEmail(), student.getComment(),student.getClass_id()};
        int rows = 0;
        try {
            rows = qr.update(sql, obj);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    //删除学生
    public int  deleteStudent(String[] students) {
        String sql="delete from `lagou_student` where stu_id=?";
        int rows=0;
        for (int i = 0; i < students.length; i++) {
            try {
                rows = qr.update(sql, students[i]);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //返回受影响的行数
        return rows;
    }

    @Override
    //查看学生的id是否存在（去重）
    public Student checkStudent(String stu_id) {
        String sql = "select *from `lagou_student` where stu_id=?";
        Student student = null;
        try {
            student = qr.query(sql, new BeanHandler<Student>(Student.class), stu_id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return student;
    }

    @Override
    //根据需求修改学生的信息 通过学生的id  找到要修改的学生
    public int repeatStudent(String stu_id_before,Student student) {
        String sql="UPDATE `lagou_student` SET stu_id=?,NAME=?,sex=?,birthday=?,email=?,COMMENT=?,class_id=?WHERE stu_id=?";
        Object [] obj=new Object[]{student.getStu_id(),student.getName(),student.getSex(),student.getBirthday(),student.getEmail(),student.getComment(),student.getClass_id(),stu_id_before};
        int rows=0;
        try {
            rows = qr.update(sql, obj);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  rows;
    }

    //查询有指定的班级有多少学生
    @Override
    public int findNumByClassId(String class_id) {
            String sql="select count(*) from `lagou_student` where class_id=?";
        int i=0;
           try {
            Long query = qr.query(sql, new ScalarHandler<Long>(), class_id);
             i = query.intValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
           return i;
    }

    /***
     * 分页查询学生的数据
     * @param start
     * @param rows
     * @return
     */
    @Override
    public List<Student> findStudentByPage(int start, int rows) {
        String sql="select *from `lagou_student`  limit ?,?";
        List<Student> list=null;
        try {
            list = qr.query(sql, new BeanListHandler<Student>(Student.class), start, rows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }


    @Override
    public int findTotalStudent() {
        String sql = "SELECT COUNT(*) FROM `lagou_student`";
        int num = 0;
        try {
            Long rows = qr.query(sql, new ScalarHandler<Long>());
            num = rows.intValue();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return num;

    }
}
