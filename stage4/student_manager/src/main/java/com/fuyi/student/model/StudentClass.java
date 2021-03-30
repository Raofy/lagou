package com.fuyi.student.model;

public class StudentClass {
    private String  grade_name;//年级
    private  String class_id;//班级编号
    private String  class_name;//班级名称
    private String  class_slogan;//班级口号
    private String  class_teacher;//班主任
    private String  class_num;//班级人数

    public StudentClass() {
    }

    public StudentClass(String grade_name, String class_id, String class_name, String class_slogan, String class_teacher) {
        this.grade_name = grade_name;
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_slogan = class_slogan;
        this.class_teacher = class_teacher;
    }

    public StudentClass(String grade_name, String class_id, String class_name, String class_slogan, String class_teacher, String class_num) {
        this.grade_name = grade_name;
        this.class_id = class_id;
        this.class_name = class_name;
        this.class_slogan = class_slogan;
        this.class_teacher = class_teacher;
        this.class_num = class_num;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_slogan() {
        return class_slogan;
    }

    public void setClass_slogan(String class_slogan) {
        this.class_slogan = class_slogan;
    }

    public String getClass_teacher() {
        return class_teacher;
    }

    public void setClass_teacher(String class_teacher) {
        this.class_teacher = class_teacher;
    }

    public String getClass_num() {
        return class_num;
    }

    public void setClass_num(String class_num) {
        this.class_num = class_num;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "grade_name='" + grade_name + '\'' +
                ", class_id='" + class_id + '\'' +
                ", class_name='" + class_name + '\'' +
                ", class_slogan='" + class_slogan + '\'' +
                ", class_teacher='" + class_teacher + '\'' +
                ", class_num='" + class_num + '\'' +
                '}';
    }
}
