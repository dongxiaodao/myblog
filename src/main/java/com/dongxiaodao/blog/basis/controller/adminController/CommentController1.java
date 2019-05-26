package com.dongxiaodao.blog.basis.controller.adminController;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.controller.BaseController;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.service.CommentsService;
import com.dongxiaodao.blog.basis.service.ContentsService;
import com.dongxiaodao.blog.basis.service.SiteService;
import com.dongxiaodao.blog.basis.util.Constant;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by ghost 2018/3/11 11:19
 */
@Controller
@RequestMapping(value = "admin/comments")
public class CommentController1 extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(CommentController1.class);

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private ContentsService contentsService;
    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "" ,method = RequestMethod.GET)
    public String index(@RequestParam(value = "page",defaultValue = "1") int page,
                        HttpServletRequest request){
        PageInfo cmsCommentsPage = commentsService.getComments(getUid(),page, Constant.SEARCH_LIMIT);
        request.getSession().setAttribute("articles",cmsCommentsPage);
        return "admin/comment_list";
    }

    /**
     * 删除一条评论
     * @param coid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public Map<String,Object> delete(@RequestParam int coid, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<>();
        try{
            CmsComments comments = commentsService.byId(coid);
            if(null==comments){
                map.put("msg","不存在该评论");
                map.put("success",false);
                return map;
            }
            commentsService.delete(coid,comments.getCid());
            siteService.cleanCache(Type_C.C_STATISTICS);
            siteService.cleanCache(Type_C.F_RECENTCOMMENT);
        }catch (Exception e){
            String msg = "评论删除失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success",false);
            return map;
        }
        map.put("success",true);
        return map;
    }
//设置一条评论的状态：公开或者隐藏
    @ResponseBody
    @RequestMapping(value = "/status",method = RequestMethod.POST)
    public Map<String,Object> status(@RequestParam int coid, @RequestParam String status){
        HashMap<String,Object> map = new HashMap<>();
        try{
            CmsComments comments = commentsService.byId(coid);
            comments.setStatus(status);
            commentsService.update(comments);
            siteService.cleanCache(Type_C.C_STATISTICS);
            siteService.cleanCache(Type_C.F_RECENTCOMMENT);
        }catch (Exception e){
            String msg = "操作失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success",false);
            return map;
        }
        map.put("success",true);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map<String,Object> reply(@RequestParam String content, @RequestParam Integer coid, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<>();
        if(null == coid || StringUtils.isBlank(content)){
            map.put("msg","请输入完整后评论");
            map.put("success",false);
            return map;
        }
        if(content.length()>2000){
            map.put("msg","请输入2000个字符以内的回复");
            map.put("success",false);
            return map;
        }
        CmsComments parent = commentsService.byId(coid);
        if(null==parent){
            map.put("msg","不存在该评论");
            map.put("success",false);
            return map;
        }
        content = DaoUtils.cleanXSS(content);
//        content = EmojiParser.parseToAliases(content);
        CmsComments comments = new CmsComments();
        SysUsers users = this.user();
        comments.setAuthor(users.getUsername());
        comments.setAuthorId(users.getUid());
        comments.setContent(content);
        comments.setIp(DaoUtils.getIp(request));
        comments.setParent(coid);
        comments.setStatus(Constant.INDEX_COMMENTS_OPEN);

        if(StringUtils.isNotBlank(users.getEmail())){
            comments.setMail(users.getEmail());
        }else {
            comments.setMail("ygxiaoxmu@163.com");
        }

        comments.setCid(commentsService.byId(coid).getCid());

        try{
            commentsService.saveComment(comments);
            siteService.cleanCache(Type_C.C_STATISTICS);
            siteService.cleanCache(Type_C.F_RECENTCOMMENT);
        }catch (Exception e){
            String msg = "回复失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success", false);
            return map;
        }
        map.put("success",true);

        //回复邮件提醒
        if(parent.getMail()!=null){
            CmsContents commentArticle = contentsService.selectByPrimaryKey(parent.getCid());
            if(commentArticle!=null){
                try {
                    DaoUtils.sendMail(parent.getMail(),commentArticle.getTitle(),parent.getAuthor(),"www.xaioyugui.cn/article/"+commentArticle.getCid()+".html#comment-"+comments.getParent());
                    LOGGER.info("邮件提醒发送成功...to "+parent.getMail());
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                } finally {
                    return map;
                }
            }
        }
        return map;
    }




}
