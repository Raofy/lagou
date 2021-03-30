package com.fuyi.student.service;

import com.fuyi.student.dao.classdao.StudentClassDao;
import com.fuyi.student.factory.StudentClassFactory;
import com.fuyi.student.model.Page;
import com.fuyi.student.model.StudentClass;

import java.util.List;


public class StudentClassService {

private StudentClassDao studentClassDao;

    /**
     *  无参构造方法，工厂模式获取studentClass类的操作类
     */
    public StudentClassService() {
        this.studentClassDao = StudentClassFactory.createStudentClassDao();
    }

    /**
     * 添加班级
     *
     * @param studentClass
     * @return
     */
    public  int addClass(StudentClass studentClass) {
        return  studentClassDao.addClass(studentClass);
    }

    /**
     * 删除班级(根据发送来的班级的编号进行删除的操作)
     *
     * @param class_ids
     * @return
     */
    public List<String> delClass(String [] class_ids){
        return studentClassDao.delClass(class_ids);
    }

    /**
     * 修改班级
     *
     * @param studentClass
     * @param class_id
     * @return
     */
    public int repeatClass(StudentClass studentClass, String  class_id){
        return  studentClassDao.repeatClass(studentClass,class_id);
    }

    /**
     * 去重检查是否存在同名的班级编号
     *
     * @param class_id
     * @return
     */
    public StudentClass checkClass(String class_id){
        return studentClassDao.checkClass(class_id);
    }
    //查找班级
    public StudentClass searchClass(String class_id){
        return studentClassDao.searchClass(class_id);
    }
    //展示班级信息
    public List<StudentClass> showAllClass(){
        return studentClassDao.showAllClass();
    }
    //查询班级的总数
    public  int findTotalStudentClass(){
     return studentClassDao.findTotalStudentClass();
    }

    //通过Page找到班级的信息
    public Page<StudentClass> findStudentClassByPage(String _current, String _rows){
        System.out.println(_current);
        System.out.println(_rows);
        int current=Integer.parseInt(_current);
        int rows=Integer.parseInt(_rows);
        //创建一个空的页对象
        Page<StudentClass> page=new Page<StudentClass>();
        //设置属性值
        page.setCurrentPage(current);
        page.setRows(rows);
        //调用dao层查询总记录数
        int totalStudentClass = findTotalStudentClass();
        //设置总条数
        page.setTotalCount(totalStudentClass);
        //计算
        int start=(current-1)*rows;
        //调用dao层查询list集合
        List<StudentClass> list = studentClassDao.findStudentClassByPage(start,rows);
        //设置list集合
        page.setList(list);
        //设置总页数
        int pages=totalStudentClass%rows==0 ? (totalStudentClass/rows) : (totalStudentClass/rows) + 1;
        page.setTotalPage(pages);
        return  page;
    }

}
