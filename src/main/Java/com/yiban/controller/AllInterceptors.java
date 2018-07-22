package com.yiban.controller;

import cn.yiban.open.Authorize;
import static com.yiban.entity.AppContent.*;

import net.sf.json.JSONObject;
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
public class AllInterceptors implements HandlerInterceptor {

    private Logger logger=LoggerFactory.getLogger(AllInterceptors.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String yibanId = (String) session.getAttribute("yiban_id");
        //除了开始以及错误界面外，其他请求应该都包含yibanId以及md5两个值
        if (request.getRequestURI().contains("error"))
            return true;
        if ( yibanId == null || yibanId.equals("")) {
//            logger.error("出现非法请求，跳转至错误页面");
//            request.getRequestDispatcher("/collect/error").forward(request,response);

            Authorize authorize = new Authorize(appKey, appSecret);
            String code = request.getParameter("code");
            logger.info("获取的code：{}", code);
            if (code == null || "".equals(code.trim())) {
                String url = authorize.forwardurl(callbackUrl, "test", Authorize.DISPLAY_TAG_T.WEB);//易班授权
                response.sendRedirect(url);
                return false;
            } else {
                JSONObject object = JSONObject.fromObject(authorize.querytoken(code, callbackUrl));
                logger.info("授权后的toke：{}", object);
                if (object.has("access_token")) {
                    String userId = object.getString("userid");
                    String accessToken = object.getString("access_token");
                    session.setAttribute("yiban_id", userId);
                    session.setAttribute("accessToken", accessToken);
                    request.getRequestDispatcher("/collect/index").forward(request,response);//重定向到首页
                    return false;
                } else {
                    return false;
                }
            }
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
