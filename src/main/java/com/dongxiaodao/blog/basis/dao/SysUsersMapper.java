package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.bean.SysUsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUsersMapper {
    long countByExample(SysUsersExample example);

    int deleteByExample(SysUsersExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(SysUsers record);

    int insertSelective(SysUsers record);

    List<SysUsers> selectByExample(SysUsersExample example);

    SysUsers selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") SysUsers record, @Param("example") SysUsersExample example);

    int updateByExample(@Param("record") SysUsers record, @Param("example") SysUsersExample example);

    int updateByPrimaryKeySelective(SysUsers record);

    int updateByPrimaryKey(SysUsers record);
}