package com.fuyi.student.dao.studentdao;

import com.fuyi.student.model.Student;

import java.util.List;

public interface StudentDao {

    /**
     * 添加学生
     *
     * @param student
     * @return
     */
    int addStudent(Student student);

    /**
     * 删除学生
     *
     * @param students
     * @return
     */
    int deleteStudent(String[] students);

    /**
     * 检查学生id是否存在相同
     *
     * @param stu_id
     * @return
     */
    Student checkStudent(String stu_id);

    /**
     * 修改学生的信息
     *
     * @param stu_id_before
     * @param student
     * @return
     */
    int repeatStudent(String stu_id_before, Student student);

    /**
     * 查询班级编号对应的学生的数量
     *
     * @param class_id
     * @return
     */
    int findNumByClassId(String class_id);

    /**
     * 分页查询数据库中的数据
     *
     * @param start
     * @param rows
     * @return
     */
    List<Student> findStudentByPage(int start, int rows);

    /**
     * 查询总共有多少条数据
     *
     * @return
     */
    int findTotalStudent();
}
