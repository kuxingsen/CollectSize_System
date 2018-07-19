package com.yiban.controller;

import com.yiban.entity.Dictionary;
import com.yiban.entity.Result;
import com.yiban.entity.Student;
import com.yiban.service.student.informationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <p>Title:处理学生信息的controller </p>
 * <p>Description: </p>
 *
 * @author 郑达成
 * @date 2018/7/15 12:37
 */
@Controller
@RequestMapping(value = "/collect")
public class InformationController {

    @Autowired
    private informationHandler informationHandler;

    private Logger logger=LoggerFactory.getLogger(InformationController.class);
    @RequestMapping(value = "/index")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/student/index");
        return modelAndView;
    }

    /**
     * @param request
     * @param response
     * @return 返回学生的个人信息--回显信息
     */
    @RequestMapping(value = "/studentInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result getStuInformation(HttpServletRequest request, HttpServletResponse response) {
        Student student = null;
        HttpSession session = request.getSession();
        String yiban_id = (String) session.getAttribute("yiban_id");
        if ((student = informationHandler.select(yiban_id)) != null) {
            String flag = "1";//数据库有这个学生的记
            session.setAttribute("flag",flag);
           return new Result<Student>(Dictionary.SUCCESS,student);
        }

        return new Result(Dictionary.FAIL_OPERATION);
    }

    /**
     *
     * @return 插入或更新学生信息的controller
     */
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    @ResponseBody
    public Result stuInfo(Student student,HttpSession session){
        System.out.println(student);
        String yiban_id= (String) session.getAttribute("yiban_id");
        student.setYiban_id(yiban_id);
        String flag= (String) session.getAttribute("flag");
        //获取学生信息
        if (flag == null || flag.equals("")){
            Result result=informationHandler.insert(student);
//            Result result = new Result<Student>();
//            result.setCode(0);
//            result.setMsg("sssss");
            logger.info("result的值，{}",result.toString());
            return result;
//            return informationHandler.insert(student);
        }else {
            return informationHandler.updateStudentBaseInfo(student);
        }
    }
    @RequestMapping(value ="/error")
    public String error(){
        return "error";
    }
}
