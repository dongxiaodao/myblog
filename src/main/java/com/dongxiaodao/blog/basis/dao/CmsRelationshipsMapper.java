package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsRelationships;
import com.dongxiaodao.blog.basis.bean.CmsRelationshipsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsRelationshipsMapper {
    long countByExample(CmsRelationshipsExample example);

    int deleteByExample(CmsRelationshipsExample example);

    int insert(CmsRelationships record);

    int insertSelective(CmsRelationships record);

    List<CmsRelationships> selectByExample(CmsRelationshipsExample example);

    int updateByExampleSelective(@Param("record") CmsRelationships record, @Param("example") CmsRelationshipsExample example);

    int updateByExample(@Param("record") CmsRelationships record, @Param("example") CmsRelationshipsExample example);
}