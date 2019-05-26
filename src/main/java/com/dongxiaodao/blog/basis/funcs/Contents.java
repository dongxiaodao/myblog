package com.dongxiaodao.blog.basis.funcs;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.dao.CmsCommentsMapper;
import com.dongxiaodao.blog.basis.service.CommentsService;
import com.dongxiaodao.blog.basis.service.ContentsService;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.SpringContextUtils;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.apache.commons.lang3.StringUtils;


import java.util.Arrays;
import java.util.List;
//导入字符串处理工具类
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 页面上自定义标签（带函数）的类
 * @author dongxiaodao
 * @date 2019/4/4 - 17:14
 */
public class Contents {

    public static String commentAt(int coid){
        CmsComments cmsComments = commentsService.getCommentsByCoid(coid);
        if(cmsComments != null){
            return "<a href= \"#comment-"+ coid +"\">@" + cmsComments.getAuthor() + "</a>";
        }
        return "";
    }
    /**
     * 显示标签
     * @param tags
     * @return
     */
    public static List<String> showTags(String tags){
        if(StringUtils.isNotBlank(tags)){
            String[] arr = tags.split(",");
            return Arrays.asList(arr);
        }
        return null;
    }

    private static final String[] ICONS = {"bg-ico-book", "bg-ico-game", "bg-ico-note", "bg-ico-chat", "bg-ico-code", "bg-ico-image", "bg-ico-web", "bg-ico-link", "bg-ico-design", "bg-ico-lock"};

    public static String showIcon(int cid){
        return ICONS[cid % ICONS.length];
    }

    /**
     * 获取最近评论
     * @param limit
     * @return
     */
    private static CommentsService commentsService = SpringContextUtils.getBean(CommentsService.class);
    public static List<CmsComments> recentComments(int limit){
        if(limit < 0 || limit >0){
            limit = 8;
        }
        return commentsService.recentComments(limit);
    }


//    可以通过ApplicationContext的getBean方法来获取Spring容器中已初始化的bean

    /**
     * 获取热门文章
     * @param limit
     * @return
     */
    private static ContentsService contentsService = SpringContextUtils.getBean(ContentsService.class);;

    public static List<CmsContents> showHotArticle(int limit){
        if(limit < 0 || limit >0){
            limit = 8;
        }
        return contentsService.getHotArticle(Type_C.HOT_ARTICLE,limit);
    }
    /**
     * 获取在首页的文章模块的文章摘要
     * @param cmsContents 用来获取内容
     * @param num 摘要字数限制
     * @return
     */
    public static String showIntro(CmsContents cmsContents,int num){
        String text = null;
        if(cmsContents.getContent() != null){
            //转化为text（去除html标签）
            text = DaoUtils.htmlToText(article_type_change(cmsContents));
            if(text.length() > num){
                return text.substring(0, num);
            }
            if(text.length() <= num){
                return text;
            }
        }
        return text;
    }
    public static String showIntro_Com(CmsComments cmsComments,int num){
        String text = null;
        if(cmsComments.getContent() != null){
            //转化为text（去除html标签）
            text = DaoUtils.htmlToText(article_type_change_Com(cmsComments));
            if(text.length() > num){
                return text.substring(0, num);
            }
            if(text.length() <= num){
                return text;
            }
        }
        return text;
    }

    private static String article_type_change_Com(CmsComments cmsComments) {
        String old_content = cmsComments.getContent();
        if (StringUtils.isNotBlank(old_content)) {
                return old_content;
        }
        return "";
    }

    /**
     * 获取在首页的文章模块的展示缩略图（文章的第一张图否则为系统随机分配的一张图）
     *
     * @return 返回缩略图
     */
    public static String showThumbnail(CmsContents cmsContents){
        if(cmsContents == null){
            return "";
        }
////        理论上也不会进到这个方法，因为数据这一列都是空的？(保留)
//        if(StringUtils.isNotBlank(cmsContents.getThumbImg())){
//            return cmsContents.getThumbImg().substring(1);
//        }
//        1.尝试获取文章内部的第一张图片
        String new_content = article_type_change(cmsContents);//判断fmt_type是markdown还是html
        String img = get_thumb(new_content);//获取文章中第一张图
        if(StringUtils.isNotBlank(img)){
            return img;
        }
//        2.文章内部没有图，进入随机分配图模式
        int cid = cmsContents.getCid();
        int size = cid % 20;
        size = size == 0?1:size;
        return "static/themes/img/rand/"+size+".jpg";
    }

    //markdown --> html
    private static String article_type_change(CmsContents cmsContents) {
        String old_content = cmsContents.getContent();
        if (StringUtils.isNotBlank(old_content)) {
            //不懂下列语句的作用
//            old_content = old_content.replace("<!--more-->", "\r\n");
            if(cmsContents.getFmtType() == "html"){
                return old_content;
            }
//            若非html，则从md转化为html
            return DaoUtils.mdToHtml(old_content);
        }
        return "";
    }


    /**
     * 获取文章第一张图片
     *
     * @return
     */
    public static String get_thumb(String content) {
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<(img|IMG)(.*?)(/>|></img|IMG>|>)";
            Pattern p_img = Pattern.compile(regEx_img);
            Matcher m_img = p_img.matcher(content);
//            判断是否有img标签
            if(m_img.find()){
//                获取到匹配的img标签中的内容
                String str_img = m_img.group(2);
//                开始匹配src
                String regEx_src = "(src|SRC)=(\"|\')(.*?)(\"|\')";
                Pattern p_src = Pattern.compile(regEx_src);
                Matcher m_src = p_src.matcher(str_img);
                if(m_src.find()){
                    return m_src.group(3);
                }
            }
        }
        return "";
    }
    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};
    public static String randColor(){
        int r = rand(0, COLORS.length - 1);
        return COLORS[r];
    }
    private static int rand(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

}
