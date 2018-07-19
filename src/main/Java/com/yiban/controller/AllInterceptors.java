package com.yiban.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>Title:拦截器 </p>
 * <p>Description: </p>
 *
 * @author 郑达成
 * @date 2018/7/15 12:36
 */
@Component
public class AllInterceptors implements HandlerInterceptor {

    private Logger logger=LoggerFactory.getLogger(AllInterceptors.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String yibanId = (String) session.getAttribute("yiban_id");
        //除了开始以及错误界面外，其他请求应该都包含yibanId以及md5两个值
        if (request.getRequestURI().contains("error")||request.getRequestURI().contains("index"))
            return true;
        if ( yibanId == null) {

            logger.error("出现非法请求，跳转至错误页面");
            request.getRequestDispatcher("/collect/error").forward(request,response);
//            http://localhost:8080/student/record/201624133115
            return false;
        } else {
            return true;
        }
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
