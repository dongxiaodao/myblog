package com.dongxiaodao.blog.basis.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongxiaodao
 * @date 2019/4/2 - 19:47
 */
public class BaseInterceptor implements HandlerInterceptor{

    public static String path;//定义为全局变量
    public static String ctx;

    //HttpServletRequest：客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中

    /**
     *
     * 这个方法在业务处理器（Controller）处理请求之前被调用，可以在该方法中对用户请求request进行处理
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //以下为完整的根目录，方便之后在页面转发到某面时使用作为根路径
        path = httpServletRequest.getContextPath();//Web应用名
        ctx = httpServletRequest.getScheme() + "://" +//返回当前链接使用的协议 ：http或者https
                httpServletRequest.getServerName() + ":" +//返回服务器名字
                httpServletRequest.getServerPort() + path + "/";//返回端口号
        httpServletRequest.setAttribute("ctx", ctx);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
