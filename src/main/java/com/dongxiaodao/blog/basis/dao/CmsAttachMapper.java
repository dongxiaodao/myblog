package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsAttach;
import com.dongxiaodao.blog.basis.bean.CmsAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsAttachMapper {
    long countByExample(CmsAttachExample example);

    int deleteByExample(CmsAttachExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CmsAttach record);

    int insertSelective(CmsAttach record);

    List<CmsAttach> selectByExample(CmsAttachExample example);

    CmsAttach selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CmsAttach record, @Param("example") CmsAttachExample example);

    int updateByExample(@Param("record") CmsAttach record, @Param("example") CmsAttachExample example);

    int updateByPrimaryKeySelective(CmsAttach record);

    int updateByPrimaryKey(CmsAttach record);
}