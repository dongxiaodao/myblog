package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.CmsContentsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsContentsMapper {
    long countByExample(CmsContentsExample example);

    int deleteByExample(CmsContentsExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(CmsContents record);

    int insertSelective(CmsContents record);

    List<CmsContents> selectByExampleWithBLOBs(CmsContentsExample example);

    List<CmsContents> selectByExample(CmsContentsExample example);
////    为获取热门文章新定义一个方法
//    List<CmsContents> selectByExampleForHot(CmsContentsExample example);

    CmsContents selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") CmsContents record, @Param("example") CmsContentsExample example);

    int updateByExampleWithBLOBs(@Param("record") CmsContents record, @Param("example") CmsContentsExample example);

    int updateByExample(@Param("record") CmsContents record, @Param("example") CmsContentsExample example);

    int updateByPrimaryKeySelective(CmsContents record);

    int updateByPrimaryKeyWithBLOBs(CmsContents record);

    int updateByPrimaryKey(CmsContents record);
}