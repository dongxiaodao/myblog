package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsMetas;
import com.dongxiaodao.blog.basis.bean.CmsMetasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsMetasMapper {
    long countByExample(CmsMetasExample example);

    int deleteByExample(CmsMetasExample example);

    int deleteByPrimaryKey(Integer mid);

    int insert(CmsMetas record);

    int insertSelective(CmsMetas record);

    List<CmsMetas> selectByExample(CmsMetasExample example);

    CmsMetas selectByPrimaryKey(Integer mid);

    int updateByExampleSelective(@Param("record") CmsMetas record, @Param("example") CmsMetasExample example);

    int updateByExample(@Param("record") CmsMetas record, @Param("example") CmsMetasExample example);

    int updateByPrimaryKeySelective(CmsMetas record);

    int updateByPrimaryKey(CmsMetas record);
}