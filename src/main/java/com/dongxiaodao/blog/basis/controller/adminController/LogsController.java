package com.dongxiaodao.blog.basis.controller.adminController;

import com.dongxiaodao.blog.basis.bean.SysLogs;
import com.dongxiaodao.blog.basis.controller.BaseController;
import com.dongxiaodao.blog.basis.service.LogService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by ghost 2018/3/11 11:19
 */
@Controller
@RequestMapping("admin/logs")
public class LogsController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ContentController.class);

    @Autowired
    LogService logService;


    /**
     * 日志管理首页
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(value="page", defaultValue="1") int page, HttpServletRequest request) {
        PageInfo pages = logService.getLogs(null,page,20);
        request.getSession().setAttribute("articles",pages);
        return "admin/logs_list";
    }

    /**
     * 日志查询
     *
     * @return
     */
    @RequestMapping(value = "/search")
    public String search(@RequestParam(value="page", defaultValue="1") int page, HttpServletRequest request) {
        String log_ip = request.getParameter("ip");
        String log_data = request.getParameter("data");
        String log_authorId = request.getParameter("authorId");
        String log_date = request.getParameter("created");

        SysLogs logs = new SysLogs();
        if(StringUtils.isNotBlank(log_ip))
            logs.setIp(log_ip);
        if(StringUtils.isNotBlank(log_data))
//            通过data去查询有问题，后台查询方法用criteria去equalTo，加%不能实现like功能
            logs.setData("%"+log_data+"%");
        if(StringUtils.isNotBlank(log_authorId))
            logs.setAuthorId(Integer.valueOf(log_authorId));
        if(StringUtils.isNotBlank(log_date)){
            Date date = new Date();
            log_date += " 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(log_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            logs.setCreated(date);
        }
        PageInfo pages = logService.getLogs(logs,page,30);
        request.getSession().setAttribute("articles",pages);

        request.getSession().setAttribute("log_ip",log_ip);
        request.getSession().setAttribute("log_data",log_data);
        request.getSession().setAttribute("log_created",log_date);
        request.getSession().setAttribute("page",page);
        request.getSession().setAttribute("log_authorId",log_authorId);

        return "admin/logs_list";
    }

}
