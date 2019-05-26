package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsOptions;
import com.dongxiaodao.blog.basis.bean.CmsOptionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsOptionsMapper {
    long countByExample(CmsOptionsExample example);

    int deleteByExample(CmsOptionsExample example);

    int deleteByPrimaryKey(String name);

    int insert(CmsOptions record);

    int insertSelective(CmsOptions record);

    List<CmsOptions> selectByExampleWithBLOBs(CmsOptionsExample example);

    List<CmsOptions> selectByExample(CmsOptionsExample example);

    CmsOptions selectByPrimaryKey(String name);

    int updateByExampleSelective(@Param("record") CmsOptions record, @Param("example") CmsOptionsExample example);

    int updateByExampleWithBLOBs(@Param("record") CmsOptions record, @Param("example") CmsOptionsExample example);

    int updateByExample(@Param("record") CmsOptions record, @Param("example") CmsOptionsExample example);

    int updateByPrimaryKeySelective(CmsOptions record);

    int updateByPrimaryKeyWithBLOBs(CmsOptions record);

    int updateByPrimaryKey(CmsOptions record);
}