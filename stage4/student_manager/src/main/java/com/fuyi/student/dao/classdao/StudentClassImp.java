package com.fuyi.student.dao.classdao;

import com.fuyi.student.dao.studentdao.StudentDaoImp;
import com.fuyi.student.model.StudentClass;
import com.fuyi.student.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*学生班级的实现类*/
public class StudentClassImp implements StudentClassDao {
    //引入学生的实现类
    StudentDaoImp studentDaoImp = new StudentDaoImp();
    static    QueryRunner qr=new QueryRunner(DruidUtils.getDataSource());

    /**
     * 添加班级的方法
     *
     * @param studentClass
     * @return 返回的发生改变的行数
     */
    @Override
    public int addClass(StudentClass studentClass) {
        //查看数据库中是否含有这个班级
        StudentClass studentClass1 = checkClass(studentClass.getClass_id());
        //如果没有进行添加
        int rows = 0;
        if (studentClass1 == null) {
            //编写添加语句sql
            String sql = "insert  into `lagou_class` (`grade_name`,`class_id`,`class_name`,`class_slogan`,`class_teacher`,`class_num`) values(?,?,?,?,?,?)";
            //添加时的学生的数量的根据学生表中的编辑的编号而确定
            //num为该班级多对应的学生的人数
            int num = serchNum(studentClass.getClass_id());
            Object[] objects = new Object[]{studentClass.getGrade_name(), studentClass.getClass_id(), studentClass.getClass_name(), studentClass.getClass_slogan(), studentClass.getClass_teacher(), num};
            //设定发生改变的行为0;
            try {
                rows = qr.update(sql, objects);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return rows;
            //返回发生改变的行数
        } else {
            return rows;
        }
    }

    //查询给定的班级的id查到对应的班级的人数
    @Override
    public int serchNum(String class_id) {
        //查询给定的班级内的学生的数量的sql语句
        String sql = "SELECT COUNT(*) FROM `lagou_student` WHERE class_id=?";
        int num = 0;
        try {
            //返回long类型的数据
            Long num1 = qr.query(sql, new ScalarHandler<Long>(), class_id);
            //将数据转化为int类型
            num = num1.intValue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将数据返回
        return num;
    }

    //删除选中的班级 前提是不能删除有学生的班级
    @Override
    public List<String> delClass(String[] arr) {
        List<String> list = new ArrayList<>();
        //删除的sql
        String sql = "delete from `lagou_class` where class_id=?";
        //遍历集合
        for (String data : arr) {
            try {
                int num = serchNum(data);
                //判断该班级是否有学生 如果有学生
                if (num == 0) {
                    qr.update(sql, data);
                } else {
                    //如果data对应的班级的学生的数量不为0那么就不能删除 ,将其添加到数组中 就将其返回
                    list.add(data);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    //查看班级的编号是否存在（去重）
    public StudentClass checkClass(String stu_id) {
        String sql = "select *from `lagou_class` where class_id=?";
        StudentClass studentClass = null;
        try {
            studentClass = qr.query(sql, new BeanHandler<StudentClass>(StudentClass.class), stu_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentClass;
    }

    //修改班级的信息
    @Override
    public int repeatClass(StudentClass studentClass, String class_id) {
        //修改班级信息的sql
        String sql = "UPDATE `lagou_class` SET grade_name=?,class_name=?,class_id=?,class_slogan=?,class_teacher=? WHERE class_id=?";
        //传入参数
        Object[] objects = new Object[]{studentClass.getGrade_name(), studentClass.getClass_name(), studentClass.getClass_id(), studentClass.getClass_slogan(), studentClass.getClass_teacher(), class_id};
        int rows = 0;
        try {
            rows = qr.update(sql, objects);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //返回被修改的行数
        return rows;
    }

    //通过class_id查询班级
    @Override
    public StudentClass searchClass(String class_id) {
        String sql = "select *from `lagou_class` where class_id=?";
        StudentClass studentClass = null;
        try {
            //
            studentClass = qr.query(sql, new BeanHandler<StudentClass>(StudentClass.class), class_id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentClass;
    }

    //查询所有的班级
    @Override
    public List<StudentClass> showAllClass() {
        //查询班级信息的sql
        String sql = "select *from `lagou_class` ";
        //查询班级编号对应的学生表中的人数的sql
        String sql1 = "update `lagou_class` set class_num=? where class_id=?";
        //创建一个集合
        List<StudentClass> list = null;
        try {
            list = qr.query(sql, new BeanListHandler<StudentClass>(StudentClass.class));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (list != null) {
            for (StudentClass studentClass : list) {
                //查询每个班级编号在学生表中的对应的学生的数量  实现实时更新数据表中的人数信息
                int numByClassId = studentDaoImp.findNumByClassId(studentClass.getClass_id());
                String num = Integer.toString(numByClassId);
                try {
                    //对应的每一条数据都做一个更新
                    qr.update(sql1, numByClassId, studentClass.getClass_id());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //将实时的数据更新集合 发送到前台
                studentClass.setClass_num(num);
            }
        }
        //返回集合
        return list;
    }

    //查询班级的总数
    @Override
    public int findTotalStudentClass() {
        String sql = "SELECT COUNT(*) FROM `lagou_class`";
        int num = 0;
        try {
            Long rows = qr.query(sql, new ScalarHandler<Long>());
            num = rows.intValue();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return num;

    }

    //通过页码查询集合
    @Override
    public List<StudentClass> findStudentClassByPage(int start, int rows) {
        //查询班级信息的sql
        String sql="select *from `lagou_class` limit ?,?";
        //查询班级编号对应的学生表中的人数的sql
        String sql1 = "update `lagou_class` set class_num=? where class_id=?";
        //创建一个集合
        List<StudentClass> list = null;
        try {
            list = qr.query(sql, new BeanListHandler<StudentClass>(StudentClass.class),start,rows);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (list != null) {
            for (StudentClass studentClass : list) {
                //查询每个班级编号在学生表中的对应的学生的数量  实现实时更新数据表中的人数信息
                int numByClassId = studentDaoImp.findNumByClassId(studentClass.getClass_id());
                String num = Integer.toString(numByClassId);
                try {
                    //对应的每一条数据都做一个更新
                    qr.update(sql1, numByClassId, studentClass.getClass_id());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //将实时的数据更新集合 发送到前台
                studentClass.setClass_num(num);
            }
        }
        //返回集合
        return list;

    }
}
