package com.dongxiaodao.blog.basis.controller;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.SysLogs;
import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.dao.VisitorMapper;
import com.dongxiaodao.blog.basis.dto.Statistics;
import com.dongxiaodao.blog.basis.service.*;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理关于用户的控制器
 * @author dongxiaodao
 * @date 2019/4/10 - 14:39
 */
@Controller
public class UsersController extends BaseController{
    public static final Logger LOGGER = Logger.getLogger(UsersController.class);



    @Autowired
    private UserService userService;
//需要导入jackson依赖，否则不能作用
    @ResponseBody
    @RequestMapping(value = "admin/login", method = RequestMethod.POST)
    public Object doLogin(SysUsers user, HttpSession session, HttpServletRequest request){
        Map<String, Object> loginMap = new HashMap<>();
        SysUsers loginUser = userService.myLogin(user.getUsername(),user.getPassword());
        if(loginUser != null){
            session.setAttribute(Type_C.LOGIN_SESSION_KEY,loginUser);
            LOGGER.info("用户：【" + getUserName() + "】登录成功。");
            loginMap.put("success",true);
            return loginMap;
        }
        String msg = "用户名或密码错误";
        loginMap.put("msg",msg);
        loginMap.put("success",false);
        return loginMap;
    }
    @Autowired
    CommentsService commentsService;
    @Autowired
    VisitorService visitorService;
    @Autowired
    ContentsService contentsService;
    @Autowired
    LogService logService;
    @RequestMapping(value = "admin/console")
    public String toConsole(HttpServletRequest request){
        List<CmsContents> contentsList = contentsService.getHotArticle(Type_C.REC_ARTICLE,8);
        List<CmsComments> commentsList = commentsService.recentComments(8);
        Statistics statistics = visitorService.getStatistic();
        SysLogs log = new SysLogs();
        log.setAuthorId(0);
        PageInfo logsPageInfo = logService.getLogs(log,1,8);
        HttpSession session = request.getSession();
        session.setAttribute("d_logs", logsPageInfo);
        session.setAttribute("d_comments",commentsList);
        session.setAttribute("d_articles", contentsList);
        session.setAttribute("statistics",statistics);
        return "admin/console";
    }
//    注销登录
    @RequestMapping(value = "admin/logout")
    public String doLogOut(HttpSession session, HttpServletRequest request){
        LOGGER.info("【" + getUserName() + "】退出系统。");
//        logService.save("退出登录",getUserName(), GhostUtils.getIp(request),1);
        session.invalidate();
        cache.clean();
        return "admin/login";
    }

}
