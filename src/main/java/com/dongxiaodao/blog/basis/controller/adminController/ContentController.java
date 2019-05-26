package com.dongxiaodao.blog.basis.controller.adminController;

import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.CmsMetas;
import com.dongxiaodao.blog.basis.controller.BaseController;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.service.*;
import com.dongxiaodao.blog.basis.util.Constant;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * Create by ghost 2018/3/11 11:19
 */
@Controller
@RequestMapping(value = "admin/article",method = RequestMethod.GET)
public class ContentController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(ContentController.class);
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private ContentsService contentsService;
    @Autowired
    private AttacheService attacheService;

    @Autowired
    private MetasService metasService;
    @Autowired
    private VisitorService visitorService;

    @RequestMapping(value = "/publish",method = RequestMethod.GET)
    public String publishArticle(HttpSession session){
        List<CmsMetas> categories = metasService.getMetas(Type_C.CATEGORY);
//        区别是发表文章还是编辑文章（null为发表新文章）
        session.setAttribute("contents",null);
        session.setAttribute("categories",categories);
        return "admin/article_edit";

    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(@RequestParam(value = "page",defaultValue = "1")int page, HttpSession session){
        page = page > Constant.MAX_PAGE ? 1:page;
        page = page <= 0?1:page;
        PageInfo pages = contentsService.searchArticle("",page,10);
        session.setAttribute("articles",pages);
        return "admin/article_list";
    }

    @RequestMapping(value = "/{cid}",method = RequestMethod.GET)
    public String editArticle(@PathVariable(value = "cid")Integer cid, HttpSession session){
        CmsContents content = contentsService.selectByPrimaryKey(cid);

        List<CmsMetas> categories = metasService.getMetas(Type_C.CATEGORY);
        session.setAttribute("contents",content);
        session.setAttribute("categories",categories);
        session.setAttribute("sorts","false");
        return "admin/article_edit";
    }
    @RequestMapping("/{cid}.html")
    public String yulan(@PathVariable("cid")Integer cid, HttpServletRequest request){
        // 为什么这里用的是request，而不是session
        CmsContents contents = contentsService.selectByPrimaryKey(cid);
        request.getSession().setAttribute("article", contents);
        request.getSession().setAttribute("is_post", true);
        return "themes/articles";
    }

//    处理发表文章的Controller
    @ResponseBody
    @RequestMapping(value = "/publish/article",method = RequestMethod.POST)
    public Map<String,Object> publishArticlePost(CmsContents contents,
                                                 HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        if (contents.getFmtType()!=null && contents.getFmtType().equals(Type_C.UE_CONTENT)){
            contents.setContent(request.getParameter("ue_content"));
        }
        contents.setType(Type_C.POST);
        contents.setHits(0);
        contents.setCommentsNum(0);
        contents.setCreated(new Date());
        contents.setModified(new Date());
        contents.setAuthorId(getUid());

        if (contents.getAllowComment()== null){
            contents.setAllowComment(0);
        }
        if (contents.getAllowFeed()==null){
            contents.setAllowFeed(0);
        }
        if (contents.getAllowPing()==null){
            contents.setAllowPing(0);
        }
        if (StringUtils.isBlank(contents.getCategories())){
            contents.setCategories("默认分类");
        }
        if (request.getParameter("sorts")!=null && request.getParameter("sorts").equals("true")){
            contents.setSort(99);
        }else{
            contents.setSort(0);
        }

       try {
           int cid = contentsService.publish(contents);

//           logService.save("发布文章", contents.getTitle(), GhostUtils.getIp(request), contents.getAuthorId());
           siteService.cleanCache(Type_C.C_STATISTICS);
           BaseController.cache.clean();
           map.put("success", true);
           map.put("payload", cid);
           LOGGER.info("发布文章,cid="+cid);
           return map;
       }catch(Exception e){
            String msg = "文章发布失败";
            if (e instanceof CustomException){
                msg = e.getMessage();
            }
            map.put("msg",msg);
            LOGGER.error(msg,e);
        }

        map.put("success",false);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Map<String, Object> publishArticleModify(CmsContents contents, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        if(contents.getFmtType().equals(Type_C.UE_CONTENT)){
            contents.setContent(request.getParameter("ue_content"));
        }

        if (null == contents.getAllowComment()) {
            contents.setAllowComment(0);
        }
        if (null == contents.getAllowFeed()) {
            contents.setAllowFeed(0);
        }
        if (null == contents.getAllowPing()) {
            contents.setAllowPing(0);
        }
        if(request.getParameter("sorts").equals("true")){
            contents.setSort(99);
        } else {
            contents.setSort(0);
        }

        contents.setModified(new Date());
        contents.setType(Type_C.POST);

        try {
            contents.setAuthorId(getUid());
            contentsService.updateArticle(contents);
            map.put("success", true);
            map.put("payload",contents.getCid());
            LOGGER.info("更新文章,cid="+contents.getCid());
            return map;
        } catch (Exception e) {
            String msg = "文章编辑失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            }
            map.put("msg",msg);
            LOGGER.error(msg, e);
        }
        map.put("success", false);
        return map;
    }

    @Autowired
    SiteService siteService;

    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String, Object> delete(@RequestParam("cid") Integer cid, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        try{
            CmsContents c = contentsService.selectByPrimaryKey(cid);
            contentsService.delete(cid);
            commentsService.deleteComment(cid);
            if (c.getThumbImg()!=null){
                attacheService.deleteAttach(c.getThumbImg());
                String prefix = request.getSession().getServletContext().getRealPath("/");
                File f = new File(prefix+c.getThumbImg());
                LOGGER.info(f.toString());
                if (!f.exists())
                    throw new CustomException("本地文件不存在");
                FileUtils.forceDelete(f);
            }
            siteService.cleanCache(Type_C.C_STATISTICS);

//            logService.save(LogActions.DEL_ARTICLE, cid+"", GhostUtils.getIp(request), this.getUid());
            LOGGER.info("id="+cid);
            map.put("success", true);
        }catch (Exception e){
            String msg = "文章删除失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success", false);
        }
        return map;
    }




}
