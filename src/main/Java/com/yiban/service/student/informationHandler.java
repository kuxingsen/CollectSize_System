package com.yiban.service.student;

import com.yiban.entity.Dictionary;
import com.yiban.entity.Result;
import com.yiban.entity.Student;
import com.yiban.mapper.studentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Title: SERVICE</p>
 * <p>Description: 处理学生信息的service类</p>
 *
 * @author 郑达成
 * @date 2018/7/16 9:22
 */
@Service
public class informationHandler {
    @Autowired
    private studentMapper studentMapper;
    //创建此类的logger
    private Logger logger = LoggerFactory.getLogger(informationHandler.class);

    /**
     * @param id
     * @return 学生个人所有的信息
     */
    public Student select(String id) {
        return studentMapper.select(id);
    }

    /**
     *
     * @param size
     * @return 转换衣服尺码
     */
    public String switchSize(int size) {
        switch (size) {
            case 0:
                return "s";
            case 1:
                return "m";
            case 2:
                return "L";
            case 3:
                return "XL";
            case 4:
                return "XXL";
            case 5:
                return "XXXL";
            default:
                return "";
        }
    }

    /**
     *
     * @param department
     * @return 转换学院名称
     */
    public String switchDeparment(int department){
        switch (department) {
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";
            case 6:
                return "";
            case 7:
                return "";
            case 8:
                return "";
            case 9:
                return "";
            case 10:
                return "";
            case 11:
                return "";
            case 12:
                return "";
            case 13:
                return "";
            case 14:
                return "";
            case 15:
                return "";
            case 16:
                return "";
            case 17:
                return "";
            case 18:
                return "";
            default:
                return "";
        }
    }
    /**
     * @param student
     * @return 更新学生基本信息
     */
    @Transactional
    public Result updateStudentBaseInfo(Student student) {
        if (studentMapper.updateStudentBaseInfo(student) == 0) {
            logger.error("更新学生信息出现失败，学生信息：{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        } else {
            return new Result(Dictionary.SUCCESS);
        }
    }

    /**
     * @param student
     * @return 把学生数据插入到数据库中
     */
    @Transactional
    public Result insert(Student student) {
        if (studentMapper.insert(student) == 0) {
            logger.error("添加学生信息出现失败，学生信息：{}", student.toString());
            return new Result(Dictionary.FAIL_OPERATION);
        }
        return new Result(Dictionary.SUCCESS);
    }
}
