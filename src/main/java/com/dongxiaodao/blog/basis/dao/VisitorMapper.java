package com.dongxiaodao.blog.basis.dao;

import com.dongxiaodao.blog.basis.dto.Archive;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**逆向工程的DAO不满足需求，新建一个DAO，添加需求
 * @author dongxiaodao
 * @date 2019/4/8 - 8:17
 */
public interface VisitorMapper {

    public CmsContents selectArticleByKeyOrSlug(String keyOrSlug);
    public List<Archive> getArchives();
    public List<MetaDto> selectMetaByTypeJoinArticle(String type);
    public MetaDto selectMetaByTypeAndName(@Param("type1") String type, @Param("name1")String name);

    List<CmsContents> getPublishArticles(Integer mid);
    public List<MetaDto> selectAllMetaByType(String type);
}
