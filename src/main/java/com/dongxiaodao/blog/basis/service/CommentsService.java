package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsCommentsExample;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.dao.CmsCommentsMapper;
import com.dongxiaodao.blog.basis.dto.Comment;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.util.MapCache;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/4/7 - 21:10
 */
@Service
public class CommentsService {
    public MapCache mapCache = MapCache.single();
    @Autowired
    CmsCommentsMapper cmsCommentsMapper;
//    获取最新评论
    public List<CmsComments>recentComments(int limit){
//        先从缓存里获取
        List<CmsComments> recent_comment= mapCache.get(Type_C.F_RECENTCOMMENT);
        if(recent_comment != null){
            return recent_comment;
        }
        if (limit <0 || limit > 20){
            limit = 10;
        }

        CmsCommentsExample cmsCommentsExample = new CmsCommentsExample();
        cmsCommentsExample.setOrderByClause("created DESC");
        PageHelper.startPage(1,limit);
//        查询后List<recent_comment>变成了pageinfo对象
          recent_comment = cmsCommentsMapper.selectByExampleWithBLOBs(cmsCommentsExample);
//        此时存入缓存的是Page对象，而不是List<recent_comment>
        mapCache.set(Type_C.F_RECENTCOMMENT,recent_comment);
        return recent_comment;
    }

//    获取dto Comment对象的集合（包含判断是否有子评论功能）
    public List<Comment> getCommentsByContents(CmsContents contents){
        if(contents != null && contents.getAllowComment() == 1){
            CmsCommentsExample example = new CmsCommentsExample();
            CmsCommentsExample.Criteria criteria = example.createCriteria();
            criteria.andCidEqualTo(contents.getCid());
//            评论未隐藏
            criteria.andStatusNotEqualTo("shield");
            example.setOrderByClause("created DESC");
//           1. 拿到父评论集合
            List<CmsComments> cmsComments = cmsCommentsMapper.selectByExampleWithBLOBs(example);
            List<Comment> comments = new ArrayList<>(cmsComments.size());

//           2. 遍历获取每个父评论的子评论

            if(cmsComments != null){
                for(CmsComments cmsComments1 : cmsComments){
                    Comment comment = new Comment(cmsComments1);
                    List<CmsComments> children = new ArrayList<>();
                    getChildren(children, comment.getCoid());

                    if(children != null && children.size() > 0){
                        comment.setLevel(1);
                        comment.setChildren(children);
                    }

                    comments.add(comment);

                }
            }
            return comments;
        }
        return null;
    }

    /**
     * 遍历每一层的子评论，添加到children list中
     * @param children final修饰 传入的子评论对象,保持对象的引用不变，维持是调用getCommentsByContents new出来的这个值
     * @param coid
     */
    @Autowired
    ContentsService contentsService;
    private void getChildren(final List<CmsComments> children, Integer coid) {
        CmsCommentsExample example = new CmsCommentsExample();
        CmsCommentsExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("shield");
        criteria.andParentEqualTo(coid);
        example.setOrderByClause("created DESC");
        List<CmsComments> cmsComments = cmsCommentsMapper.selectByExampleWithBLOBs(example);
        if(cmsComments != null){
            children.addAll(cmsComments);
//            同一层级的评论排完之后，再轮到下一层级的排，（可以优化一下）
//            以下是递归求每一层级的评论
            for(CmsComments cmsComment: cmsComments) {
                getChildren(children, cmsComment.getCoid());
            }
        }
    }

    public void saveComment(CmsComments comments) {
        if (null == comments) {
            throw new CustomException("评论对象为空");
        }
        if (StringUtils.isBlank(comments.getAuthor())) {
            throw new CustomException("姓名不能为空");
        }

        if (StringUtils.isNotBlank(comments.getMail())) {
            if (!comments.getMail().matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$")) {
                throw new CustomException("请输入正确的邮箱格式");
            }
        }

        if (StringUtils.isBlank(comments.getContent())) {
            throw new CustomException("评论内容不能为空");
        }
        if(comments.getContent().length() < 5 || comments.getContent().length() > 2000){
            throw new CustomException("评论字数在5-2000个字符");
        }
        if (null == comments.getCid()) {
            throw new CustomException("评论文章不能为空");
        }
        CmsContents contents = contentsService.selectByPrimaryKey(comments.getCid());
        if (null == contents) {
            throw new CustomException("不存在的文章");
        }
        if(null == comments.getParent()){
            comments.setParent(0);
        }
//        有优化空间，应该有一个是评论者id才对
        try {
            comments.setAuthorId(contents.getAuthorId());
            comments.setOwnerId(contents.getAuthorId());
            comments.setCreated(new Date());
            cmsCommentsMapper.insert(comments);

            contents.setCommentsNum(contents.getCommentsNum()+1);
            contentsService.updateContents(contents);
        } catch (Exception e) {
//            抛出异常，给CommentController去处理
            throw e;
        }
    }

    public CmsComments getCommentsByCoid(int coid) {
        return cmsCommentsMapper.selectByPrimaryKey(coid);
    }

    @Autowired
    CmsCommentsMapper commentsMapper;
    public void deleteComment(Integer cid) {
        CmsCommentsExample example = new CmsCommentsExample();
        CmsCommentsExample.Criteria criteria= example.createCriteria();
        criteria.andCidEqualTo(cid);
        int num = (int)commentsMapper.countByExample(example);
        if (num>0){
            commentsMapper.deleteByExample(example);
        }
    }

    public CmsComments byId(Integer coid) {
        return commentsMapper.selectByPrimaryKey(coid);

    }

    public void update(CmsComments comments) {
        commentsMapper.updateByPrimaryKey(comments);
    }

    public void delete(int coid, Integer cid) {
        try {
            commentsMapper.deleteByPrimaryKey(coid);
            CmsContents contents = contentsService.selectByPrimaryKey(cid);
            if(null != contents && contents.getCommentsNum() >0){
                contents.setCommentsNum(contents.getCommentsNum()-1);
                contentsService.update(contents);
            }
        }catch (Exception e){
            throw e;
        }
    }

    public PageInfo  getComments(Integer uid, int page, int searchLimit) {
        CmsCommentsExample example = new CmsCommentsExample();
//        CmsCommentsExample.Criteria criteria = example.createCriteria();
//        criteria.andAuthorIdEqualTo(uid);
        example.setOrderByClause("coid desc");
        int count = (int)commentsMapper.countByExample(null);

        PageHelper.startPage(page,searchLimit);
        List<CmsComments> parentsList = commentsMapper.selectByExampleWithBLOBs(example);

        PageInfo pageInfo = new PageInfo(parentsList);
        pageInfo.setTotal(count);
        return pageInfo;
    }
}
