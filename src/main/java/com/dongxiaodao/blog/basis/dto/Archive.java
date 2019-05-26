package com.dongxiaodao.blog.basis.dto;

import com.dongxiaodao.blog.basis.bean.CmsContents;

import java.util.Date;
import java.util.List;

/** 文章归档页面所用类
 * @author dongxiaodao
 * @date 2019/4/15 - 22:42
 */
public class Archive {
    private String dateTimeStr;
    private Date date;
    private Integer count;
    private List<CmsContents> articles;

    public String getDateTimeStr() {
        return dateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CmsContents> getArticles() {
        return articles;
    }

    public void setArticles(List<CmsContents> articles) {
        this.articles = articles;
    }
}
