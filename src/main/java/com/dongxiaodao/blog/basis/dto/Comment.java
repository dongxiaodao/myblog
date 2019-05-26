package com.dongxiaodao.blog.basis.dto;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsContents;

import java.util.List;

/** CmsComments dto 扩展了子评论属性 和评论等级（若有评论，则设置评论等级为1）
 * @author dongxiaodao
 * @date 2019/4/11 - 20:23
 */
public class Comment extends CmsComments{
    private int level;
    private List<CmsComments> children;


    @Override
    public String toString() {
        return "Comment{"  + super.toString()+
                "level=" + level +
                ", children=" + children +
                '}';
    }

    public Comment(CmsComments cmsComments){
        setAuthor(cmsComments.getAuthor());
        setAgent(cmsComments.getAgent());
        setAuthorId(cmsComments.getOwnerId());
        setCid(cmsComments.getCid());
        setCoid(cmsComments.getCoid());
        setContent(cmsComments.getContent());
        setCreated(cmsComments.getCreated());
        setIp(cmsComments.getIp());
        setMail(cmsComments.getMail());
        setParent(cmsComments.getParent());
        setUrl(cmsComments.getUrl());
        setType(cmsComments.getType());
        setStatus(cmsComments.getStatus());
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setChildren(List<CmsComments> children) {
        this.children = children;
    }

    public List<CmsComments> getChildren() {
        return children;
    }
}
