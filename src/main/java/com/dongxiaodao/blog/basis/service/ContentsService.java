package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.CmsContentsExample;
import com.dongxiaodao.blog.basis.bean.CmsMetas;
import com.dongxiaodao.blog.basis.bean.CmsRelationshipsExample;
import com.dongxiaodao.blog.basis.dao.CmsContentsMapper;
import com.dongxiaodao.blog.basis.dao.CmsRelationshipsMapper;
import com.dongxiaodao.blog.basis.dao.VisitorMapper;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.util.Constant;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/4/4 - 15:10
 */
@Service
public class ContentsService {
    @Autowired
    CmsContentsMapper cmsContentsMapper;

    public CmsContents selectByPrimaryKey(Integer cid){
        return cmsContentsMapper.selectByPrimaryKey(cid);
    }

    public List<CmsContents> getContents() {
        CmsContentsExample example = new CmsContentsExample();
        CmsContentsExample.Criteria criteria = example.createCriteria();
        criteria.andAllowFeedEqualTo(0);
        example.setOrderByClause("created desc");
        return cmsContentsMapper.selectByExampleWithBLOBs(example);
    }

    //获取热门文章
    public List<CmsContents> getHotArticle(String type,int limit) {
        CmsContentsExample cmsContents = new CmsContentsExample();
        CmsContentsExample.Criteria cri= cmsContents.createCriteria();
        cri.andTypeEqualTo(Type_C.POST);
        cri.andStatusEqualTo(Type_C.PUBLISH);
//        开始日志allowFeed为1
        cri.andAllowFeedEqualTo(0);
        cmsContents.setOrderByClause(type);
        PageHelper.startPage(1,limit);
        return cmsContentsMapper.selectByExample(cmsContents);
    }

//    更新点击量
    public void updateHit(CmsContents article) {
        int newHit = article.getHits() + 1;
        CmsContentsExample cmsContentsExample = new CmsContentsExample();
        CmsContentsExample.Criteria criteria = cmsContentsExample.createCriteria();
//        在该cid位置
        criteria.andCidEqualTo(article.getCid());
        CmsContents cmsContents = new CmsContents();
//        只更新Hits
        cmsContents.setHits(newHit);

        cmsContentsMapper.updateByExampleSelective(cmsContents, cmsContentsExample);

    }

    public void updateContents(CmsContents contents) {
//        带内容一起更新
        cmsContentsMapper.updateByPrimaryKeyWithBLOBs(contents);
    }

    public List<CmsContents> getContentsByCategory(String category) {
        CmsContentsExample example = new CmsContentsExample();
        CmsContentsExample.Criteria criteria = example.createCriteria();
//        排除日志
        criteria.andAllowFeedEqualTo(0);
        criteria.andStatusEqualTo(Type_C.PUBLISH);
        criteria.andTypeEqualTo(Type_C.POST);
        criteria.andCategoriesEqualTo(category);
        example.setOrderByClause("created desc");
        List<CmsContents> contents = cmsContentsMapper.selectByExampleWithBLOBs(example);
        return contents;
    }

    @Autowired
    VisitorMapper visitorMapper;
    public PageInfo getArticlesByMetaDto(Integer mid, int count, int page, int limit) {
        PageHelper.startPage(page,limit);
        List<CmsContents> list = visitorMapper.getPublishArticles(mid);
        PageInfo pageInfo_contents = new PageInfo(list);
        pageInfo_contents.setTotal(count);
        return pageInfo_contents;
    }

    public PageInfo searchArticle(String keyword, int page, int limit) {
        CmsContentsExample example = new CmsContentsExample();
        CmsContentsExample.Criteria criteria = example.createCriteria();
        criteria.andTitleLike("%"+ keyword + "%");
        criteria.andStatusEqualTo(Type_C.PUBLISH);
        criteria.andAllowFeedEqualTo(0);
        CmsContentsExample.Criteria criteria2 = example.createCriteria();
        criteria2.andStatusEqualTo(Type_C.PUBLISH);
        criteria2.andAllowFeedEqualTo(0);
        criteria2.andTagsLike("%" + keyword + "%");
        example.or(criteria2);
        example.setOrderByClause("created desc");
        PageHelper.startPage(page,limit);
        List<CmsContents> cmsContents = cmsContentsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(cmsContents);
        return pageInfo;

    }
    @Autowired
    MetasService metasService;
    public int publish(CmsContents contents) {
        if (contents.getTitle().length()> Constant.MAX_TITLE_COUNT){
            throw new CustomException("文章标题最多可输入"+Constant.MAX_TITLE_COUNT+"个字符");
        }
        int len = contents.getContent().length();
        if (len > Constant.MAX_TEXT_COUNT){
            throw new CustomException("文章内容最多可输入"+Constant.MAX_TEXT_COUNT+"个字符");
        }
        if (contents.getAuthorId()==null)
            throw new CustomException("请登录后再发表文章");
        if (StringUtils.isNotBlank(contents.getSlug())){
            contents.setSlug(contents.getSlug().trim());
            if (contents.getSlug().length()<3)
                throw new CustomException("文章路径名太短");
            if (!DaoUtils.isPath(contents.getSlug())){
                throw new CustomException("您输入的路径名不合法");
            }
            CmsContents c = visitorMapper.selectArticleByKeyOrSlug(contents.getSlug());
            if (c!=null)
                throw new CustomException("文章路径已存在");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            contents.setSlug(sdf.format(new Date()).substring(2));
        }
        cmsContentsMapper.insert(contents);
//        空指针异常
        int cid = contents.getCid();
        if (contents.getTags()!=null){
            metasService.saveMetas(cid,contents.getTags(),Type_C.TAG);
        }
        if (contents.getCategories()!=null){
            metasService.saveMetas(cid,contents.getCategories(),Type_C.CATEGORY);
        }
//        LOGGER.info(contents.toString());
        return cid;
    }

    @Autowired
    CmsRelationshipsMapper relationshipsMapper;
    public void updateArticle(CmsContents contents) {
        if (contents.getTitle().length() > Constant.MAX_TITLE_COUNT) {
            throw new CustomException("文章标题最多可以输入" + Constant.MAX_TITLE_COUNT + "个字符");
        }
        int len = contents.getContent().length();
        if (len > Constant.MAX_TEXT_COUNT)
            throw new CustomException("文章内容最多可以输入" + Constant.MAX_TEXT_COUNT + "个字符");
        if (null == contents.getAuthorId())
            throw new CustomException("请登录后发布文章");
        if (StringUtils.isNotBlank(contents.getSlug())) {
            contents.setSlug(contents.getSlug().trim());
            if (contents.getSlug().length() < 3) {
                throw new CustomException("路径太短了");
            }
            if (!DaoUtils.isPath(contents.getSlug()))
                throw new CustomException("您输入的路径不合法");
            CmsContents c = visitorMapper.selectArticleByKeyOrSlug(contents.getSlug());
            if (c!= null && c.getCid().intValue() != contents.getCid().intValue() )
                throw new CustomException("该路径已经存在，请重新输入");
        } else {
            SimpleDateFormat spf = new SimpleDateFormat("yyyyMMddHHmm");
            contents.setSlug(spf.format(new Date()).substring(2));
        }
        CmsContents oldContent = cmsContentsMapper.selectByPrimaryKey(contents.getCid());
        contents.setCreated(oldContent.getCreated());
        contents.setHits(oldContent.getHits());
        contents.setCommentsNum(oldContent.getCommentsNum());

        cmsContentsMapper.updateByPrimaryKeyWithBLOBs(contents);
        /*
        存之前应该先删除老的
         */
        CmsRelationshipsExample example = new CmsRelationshipsExample();
        CmsRelationshipsExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(contents.getCid());
        relationshipsMapper.deleteByExample(example);

        metasService.saveMetas(contents.getCid(), contents.getTags(), Type_C.TAG);
        metasService.saveMetas(contents.getCid(), contents.getCategories(), Type_C.CATEGORY);

//        LOGGER.info(contents.toString());
    }

    public void delete(Integer cid) {
        cmsContentsMapper.deleteByPrimaryKey(cid);
        CmsRelationshipsExample example = new CmsRelationshipsExample();
        CmsRelationshipsExample.Criteria criteria = example.createCriteria();
        criteria.andCidEqualTo(cid);
        relationshipsMapper.deleteByExample(example);
    }

    public void update(CmsContents contents) {
        cmsContentsMapper.updateByPrimaryKeyWithBLOBs(contents);

    }
}
