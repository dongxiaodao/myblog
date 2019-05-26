package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.SysLogs;
import com.dongxiaodao.blog.basis.bean.SysLogsExample;
import com.dongxiaodao.blog.basis.dao.SysLogsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/5/23 - 15:39
 */
@Service
public class LogService {

    @Autowired
    SysLogsMapper logsMapper;
//    获取日志信息
    public PageInfo getLogs(SysLogs log,int page, int limit){
        if (page <= 0) page = 1;
        if (limit == 0) limit = 9999;
        SysLogsExample example = new SysLogsExample();
        SysLogsExample.Criteria criteria = example.createCriteria();
        if (log!=null){
            if (log.getId()!=null){
                criteria.andIdEqualTo(log.getId());
            }
            if (log.getAuthorId()!=null){
                criteria.andAuthorIdEqualTo(log.getAuthorId());
            }
            if (log.getIp()!=null){
                criteria.andIpEqualTo(log.getIp());
            }
            if (log.getCreated()!=null){
                criteria.andCreatedEqualTo(log.getCreated());
            }
            if (log.getData()!=null){
                criteria.andDataEqualTo(log.getData());
            }
        }
        example.setOrderByClause("created DESC");
        PageHelper.startPage(page,limit);
        List<SysLogs> logsList = logsMapper.selectByExample(example);
        PageInfo logsPageInfo = new PageInfo(logsList);
        return logsPageInfo;
    }

//    记录日志信息
    public void save(String action, String userName, String ip, int authorId) {
//        if (StringUtils.equals(ip, "0:0:0:0:0:0:0:1") || StringUtils.equals(ip, "127.0.0.1")) return;
        System.out.println("进入保存日志方法");
        SysLogs sysLogs = new SysLogs();
        sysLogs.setIp(ip);
        sysLogs.setAuthorId(authorId);
        sysLogs.setAction(action);
        sysLogs.setCreated(new Date());
        sysLogs.setData(userName);
        logsMapper.insert(sysLogs);
    }
}
