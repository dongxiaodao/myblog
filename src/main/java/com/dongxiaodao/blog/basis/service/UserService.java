package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.bean.SysUsersExample;
import com.dongxiaodao.blog.basis.dao.SysUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/4/10 - 14:41
 */
@Service
public class UserService {
    @Autowired
    private SysUsersMapper sysUsersMapper;


    public SysUsers myLogin(String username, String password) {
        SysUsersExample sysUsersExample = new SysUsersExample();
        SysUsersExample.Criteria criteria = sysUsersExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<SysUsers> list = sysUsersMapper.selectByExample(sysUsersExample);//若为空，返回为[],而不是null
//        一定要+ list.size()==0
        if(null == list || list.size() ==0){
            return null;
        }
        SysUsers user = list.get(0);
        return user;
    }
}
