package com.dongxiaodao.blog.basis.dto;

/**
 * @author dongxiaodao
 * @date 2019/4/18 - 8:37
 */
public class Statistics {
    private int articleNum;

    private int commentNum;

    private int categoryNum;

    private int tagNum;
    private int attacheNum;

    public Statistics(int articleNum, int commentNum, int attacheNum) {
        this.articleNum = articleNum;
        this.commentNum = commentNum;
        this.attacheNum = attacheNum;
    }

    public Statistics() {

    }

    public int getAttacheNum() {
        return attacheNum;
    }

    public void setAttacheNum(int attacheNum) {
        this.attacheNum = attacheNum;
    }

    public int getArticleNum() {
        return articleNum;
    }

    public void setArticleNum(int articleNum) {
        this.articleNum = articleNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(int categoryNum) {
        this.categoryNum = categoryNum;
    }

    public int getTagNum() {
        return tagNum;
    }

    public void setTagNum(int tagNum) {
        this.tagNum = tagNum;
    }
}
