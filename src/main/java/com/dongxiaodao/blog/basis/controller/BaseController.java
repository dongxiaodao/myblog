package com.dongxiaodao.blog.basis.controller;

import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.MapCache;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dongxiaodao
 * @date 2019/4/12 - 19:45
 */
public class BaseController {
    protected static MapCache cache = MapCache.single();
    public SysUsers user(){
        return DaoUtils.getLoginUser();
    }
    /*
     * 在request中放入关title属性
     * */
    public BaseController title(HttpServletRequest request, String title){
        request.setAttribute("title",title);
        return this;
    }

    /*
     * 在request中放入关键字属性
     * */
    public BaseController keywords(HttpServletRequest request,String title){
        request.setAttribute("title",title);
        return this;
    }

    public Integer getUid(){
        return this.user().getUid();
    }

    public String getUserName(){
        return this.user().getUsername();
    }
}
