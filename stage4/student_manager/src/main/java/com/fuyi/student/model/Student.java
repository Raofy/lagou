package com.fuyi.student.model;


//学号、姓名、性别、出生日期、邮箱、备注

public class Student {

    private String stu_id;//学号
    private String name;//姓名
    private String sex;//性别
    private String birthday;//出生日期
    private String email;//电子邮件
    private String class_id;//班级
    private String comment;//备注

    public Student() {
    }

    public Student(String stu_id, String name, String sex, String birthday, String email, String class_id, String comment) {
        this.stu_id = stu_id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.class_id = class_id;
        this.comment = comment;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;

    }

    @Override
    public String toString() {
        return "Student{" +
                "stu_id='" + stu_id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", class_id='" + class_id + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
