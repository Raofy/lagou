package com.fuyi.student.dao.classdao;



import com.fuyi.student.model.StudentClass;

import java.util.List;

public interface StudentClassDao {

        /**
         * 添加班级
         *
         * @param studentClass
         * @return
         */
        public abstract  int  addClass(StudentClass studentClass);

    /**
     * 根据班级的编号查询该班级的学生的数量
         *
         * @param class_id
     * @return
     */
        public  abstract int serchNum(String class_id);

    /**
     * 删除班级
         *
         * @param arr
     * @return
     */
        public abstract List<String> delClass(String[] arr);

    /**
     * 去重班级编号
         *
         * @param class_id
     * @return
     */
        public abstract  StudentClass checkClass(String class_id);

    /**
     * 修改班级的信息
         *
         * @param studentClass
     * @param class_id
     * @return
     */
        public abstract int repeatClass(StudentClass studentClass, String class_id);

    /**
     * 查询班级
         *
         * @param class_id
     * @return
     */
        public abstract StudentClass searchClass(String class_id);

    /**
     * 查询所有的班级信息
         *
         * @return
     */
        public abstract List<StudentClass>  showAllClass();

    /**
     * 插叙班级的总数
         *
         * @return
     */
        public abstract int findTotalStudentClass();

    /**
     * 分页显示班级信息
         *
         * @param start
     * @param rows
     * @return
     */
        public abstract  List<StudentClass> findStudentClassByPage(int start, int rows);

}
