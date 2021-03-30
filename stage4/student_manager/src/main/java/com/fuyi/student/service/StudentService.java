package com.fuyi.student.service;


import com.fuyi.student.dao.studentdao.StudentDao;
import com.fuyi.student.factory.StudentFactory;
import com.fuyi.student.model.Page;
import com.fuyi.student.model.Student;

import java.util.List;

public class StudentService {

    private StudentDao studentDao;

    public StudentService() {
        this.studentDao = StudentFactory.createStudentDao();
    }

    /**
     * 添加学生
     */
    public int addStudnet(Student student) {
        return studentDao.addStudent(student);
    }

    /**
     * 根据学生的stu_id查询学生
     */
    public Student checkStudent(String stu_id) {
        return studentDao.checkStudent(stu_id);
    }

    /**
     * 删除学生
     */
    public int deletStudent(String[] stus) {
        return studentDao.deleteStudent(stus);
    }
    /**
     * 修改学生的信息
     * */
    public int repeatStudent(String stu_id_before, Student student){
        return  studentDao.repeatStudent(stu_id_before,student);
    }

    /**
     * 分页查询信息
     * @param _current
     * @param _rows
     * @return
     */
    public Page<Student> findStudentByPage(String _current, String _rows){
        System.out.println(_current);
        System.out.println(_rows);
        int current=Integer.parseInt(_current);
        int rows=Integer.parseInt(_rows);
        //创建一个空的页对象
        Page<Student> page=new Page<Student>();
        //设置属性值
        page.setCurrentPage(current);
        page.setRows(rows);
        //调用dao层查询总记录数
        int totalStudents= studentDao.findTotalStudent();
        //设置总条数
        page.setTotalCount(totalStudents);
        //计算
        int start=(current-1)*rows;
        //调用dao层查询list集合
        List<Student> list = studentDao.findStudentByPage(start,rows);
        //设置list集合
        page.setList(list);
        //设置总页数
        int pages=totalStudents%rows==0 ? (totalStudents/rows) : (totalStudents/rows) + 1;
        page.setTotalPage(pages);
        return  page;
    }
}
