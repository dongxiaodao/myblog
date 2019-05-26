package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsCommentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsCommentsMapper {
    long countByExample(CmsCommentsExample example);

    int deleteByExample(CmsCommentsExample example);

    int deleteByPrimaryKey(Integer coid);

    int insert(CmsComments record);

    int insertSelective(CmsComments record);

    List<CmsComments> selectByExampleWithBLOBs(CmsCommentsExample example);

    List<CmsComments> selectByExample(CmsCommentsExample example);

    CmsComments selectByPrimaryKey(Integer coid);

    int updateByExampleSelective(@Param("record") CmsComments record, @Param("example") CmsCommentsExample example);

    int updateByExampleWithBLOBs(@Param("record") CmsComments record, @Param("example") CmsCommentsExample example);

    int updateByExample(@Param("record") CmsComments record, @Param("example") CmsCommentsExample example);

    int updateByPrimaryKeySelective(CmsComments record);

    int updateByPrimaryKeyWithBLOBs(CmsComments record);

    int updateByPrimaryKey(CmsComments record);
}