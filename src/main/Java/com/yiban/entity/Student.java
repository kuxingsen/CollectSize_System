package com.yiban.entity;

import java.io.Serializable;

/**
 * <p>Title:学生的javabean </p>
 * <p>Description: </p>
 *
 * @author 郑达成
 * @date 2018/7/15 12:32
 */
public class Student implements Serializable{

    private static final long serialVersionUID = 3210359254553565348L;
    //    学生的服装尺码
    private int ssize;
    //    学生学号
    private String student_id;
    //    学生姓名
    private String name;
    //    学院
    private int department;
    //    学生对应的班级
    private String class_name;
    //    学生对应的易班id
    private String yiban_id;


    public int getSsize() {
        return ssize;
    }

    public void setSsize(int ssize) {
        this.ssize = ssize;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getYiban_id() {
        return yiban_id;
    }

    public void setYiban_id(String yiban_id) {
        this.yiban_id = yiban_id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "yibanId='" + yiban_id + '\'' +
                ", studentId='" + student_id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", className='" + class_name + '\'' +
                ", className='" + student_id + '\'' +
                '}';
    }
}
