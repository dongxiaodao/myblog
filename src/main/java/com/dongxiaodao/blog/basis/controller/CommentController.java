package com.dongxiaodao.blog.basis.controller;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.dao.CmsCommentsMapper;
import com.dongxiaodao.blog.basis.dto.LogActions;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.service.CommentsService;
import com.dongxiaodao.blog.basis.service.SiteService;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.MapCache;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongxiaodao
 * @date 2019/4/12 - 16:57
 */
@Controller
public class CommentController extends BaseController{
    public static final Logger LOGGER = Logger.getLogger(IndexController.class);
    @Autowired
    CommentsService commentsService;
    @Autowired
    CmsCommentsMapper cmsCommentsMapper;
    @Autowired
    SiteService siteService;
    public MapCache mapCache = MapCache.single();
    //@responseBody注解的作用是将controller的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML
    // json
    //　　数据，需要注意的呢，在使用此注解之后不会再走试图处理器，而是直接将数据写入到输入流中，他的效果等同于通过response对象输出指定格式的数据
    @Autowired
    IndexController indexController;
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Map<String, Object> addComment(HttpServletRequest request, CmsComments cmsComments, HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<>();
//        文章是否还在
        if(cmsComments.getCid() == null || StringUtils.isBlank(cmsComments.getAuthor())){
            map.put("msg", "请完整后评论");
            map.put("success", false);
            return map;
        }
//        评论者姓名校验
        if(cmsComments.getAuthor().length() > 20){
            map.put("msg", "请完整后评论");
            map.put("success", false);
            return map;
        }
//        评论内容校验
        if (cmsComments.getContent().length()>200){
            map.put("msg","请输入两百字以内的评论");
            map.put("success",false);
            return map;
        }

        String fre_key = cmsComments.getAuthor() + cmsComments.getCid() + cmsComments.getCoid();
        Integer count = cache.specialGet("com_frequency", fre_key);
        if(count != null){
            map.put("msg", "您的评论发表过快，请稍后再试");
            map.put("success", false);
        }
//        优化评论者内容和符号
        cmsComments.setAuthor(DaoUtils.cleanXSS(cmsComments.getAuthor()));
        cmsComments.setContent(DaoUtils.cleanXSS(cmsComments.getContent()));
//        获取访问者ip
        cmsComments.setIp(DaoUtils.getIp(request));
        cmsComments.setParent(cmsComments.getCoid());
        cmsComments.setCoid(null);//使其自增
        cmsComments.setStatus("open");
//    保存评论
        try {
            commentsService.saveComment(cmsComments);
        } catch (Exception e) {

//            若service保存评论出现异常
            String msg = "评论发布失败";
            if(e instanceof CustomException){
                msg = e.getMessage() + "自定义异常";
            }else {
                LOGGER.error(msg);
            }
            map.put("msg",msg);
            map.put("success", false);
            return map;
        }
//        添加缓存
        try {
            cache.specialSet("com_frequency",fre_key,1,30);
//        添加评论者数据以cookie形式返回给客户端，记住评论人姓名和评论人的邮箱
            Cookie author_name = new Cookie("remember_author", URLEncoder.encode(cmsComments.getAuthor(),"UTF-8"));
            author_name.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(author_name);
            if(cmsComments.getMail()!=null && StringUtils.isNotBlank(cmsComments.getMail())){
                Cookie cookie_email = new Cookie("remember_mail", URLEncoder.encode(cmsComments.getMail(), "UTF-8"));
                cookie_email.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(cookie_email);
            }
        } catch (UnsupportedEncodingException e) {
//            打印异常可以省略，因为log4j配置已经包含输出到控制台，为防止配置出错，还是添加以下语句
            e.printStackTrace();
            LOGGER.error(e);
        }
//        保存评论成功
        map.put("success",true);
        siteService.cleanCache(Type_C.F_RECENTCOMMENT);
        siteService.cleanCache(Type_C.C_STATISTICS);
        indexController.saveLog(request, LogActions.INDEX_COMMENT,"昵称：" + cmsComments.getAuthor());
        return map;
    }

}
