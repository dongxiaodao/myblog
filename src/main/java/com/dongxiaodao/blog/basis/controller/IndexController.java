package com.dongxiaodao.blog.basis.controller;

import com.dongxiaodao.blog.basis.dto.Archive;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.bean.SysUsers;
import com.dongxiaodao.blog.basis.dto.Comment;
import com.dongxiaodao.blog.basis.dto.LogActions;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import com.dongxiaodao.blog.basis.service.*;
import com.dongxiaodao.blog.basis.util.Constant;
import com.dongxiaodao.blog.basis.util.DaoUtils;
import com.dongxiaodao.blog.basis.util.Type_C;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/3/31 - 8:17
 */

/**
 * 处理index.jsp的转发请求
 */
@Controller
public class IndexController extends BaseController{
    @Autowired
    private ContentsService contentsService;
    /**
     * 转发到home.jsp页面
     * @return
     */
    @RequestMapping(value = "/toHome", method = RequestMethod.GET)
        public String toHome(@RequestParam(value = "page", defaultValue = "1")int page, HttpServletRequest request, HttpSession session){
        //若有page传入，则判断一下page是否合法
        page = page <= 0 || page > Constant.MAX_PAGE ? 1:page;
        PageHelper.startPage(page, 12);
        List<CmsContents> contents = contentsService.getContents();
//        包装查询后的结果
        PageInfo contentsPageInfo = new PageInfo(contents);
//        model添加进文章内容对象，方便首页调用显示
        session.setAttribute("article_contents", contentsPageInfo);
        saveLog(request,"访问主页",null);
        return "themes/home";
    }

//    建立一个保存日志函数，保存前台访问页面的操作日志
    @Autowired
    LogService logService;
    public void saveLog(HttpServletRequest request, String action, String data){
        String requestHead = request.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(requestHead);
//       获取操作系统
        OperatingSystem os = userAgent.getOperatingSystem();
//        获取浏览器
        Browser browser = userAgent.getBrowser();
        String device = null;//代表是日志里的用户名
        if (os.isMobileDevice()){
            device = DaoUtils.getDevice(requestHead)+"__"+os.getName()+"__"+browser.getName()+"__"+userAgent.getBrowserVersion();
        }else{
            device = "Cpt__"+os.getName()+"__"+browser.getName()+"__"+userAgent.getBrowserVersion();
        }
//        authorId:0 代表是用户 为1 代表是管理者
        logService.save(action + "->"+data,device,DaoUtils.getIp(request),0);
    }

    @RequestMapping(value = "/archives",method = RequestMethod.GET)
    public String archives(HttpSession session,HttpServletRequest request){
        List<Archive> archives = visitorService.getArchives();
        session.setAttribute("archives",archives);
        session.setAttribute("is_archive", true);
        saveLog(request,"查看文章归档页面",null);
        return "themes/archives";
    }
//
//    实现tech页面分四步
//      1.获取分类为tech的文章list，
//      2.获取所有type为category的metas对象（用作jsp页面右侧的文章分类）
//      3.获取热门文章（这里取点击量降序排列）
//      4.获取所有type为tag的metas对象（用作jsp页面右侧的热门标签）

    @RequestMapping(value = "/tech",method = RequestMethod.GET)
    public String tech(@RequestParam(value = "page", defaultValue = "1")int page, HttpServletRequest request){
        page = page <= 0 || page > Constant.MAX_PAGE ? 1:page;
//      1.获取分类为tech的文章
        PageHelper.startPage(page,8);
        List<CmsContents> contents = contentsService.getContentsByCategory("tech");
        PageInfo pageInfo = new PageInfo(contents);

        category();
        request.getSession().setAttribute("categoriesf",cache.get("categoriesf"));
//        3.获取所有type为tag的metas对象（用作jsp页面右侧的热门标签）
        if (cache.get("tagsf")==null){
            List<MetaDto> tags = visitorService.getMetas("tag");
            cache.set("tagsf",tags);
        }
        request.getSession().setAttribute("tagsf",cache.get("tagsf"));
        //4.推荐文章
        if (cache.get("realTop")==null){
            List<CmsContents> hotArticle = contentsService.getHotArticle(Type_C.HOT_ARTICLE,6);
//
//            if (hotArticle.size()>0)
//                cache.set("realTop",hotArticle.get(0));
            cache.set("recommendArticle",hotArticle);
        }
        request.getSession().setAttribute("articles",pageInfo);
        request.getSession().setAttribute("recommendArticle",cache.get("recommendArticle"));
//        request.getSession().setAttribute("realTop",cache.get("realTop"));
//
//        request.getSession().setAttribute("is_home",true);
//        request.getSession().setAttribute("page_prefix","/page");
        saveLog(request, LogActions.TECHPAGE,null);
        return "themes/tech";
    }

    @RequestMapping(value = "/life",method = RequestMethod.GET)
    public String life(@RequestParam(value = "page", defaultValue = "1")int page, HttpServletRequest request){
        page = page <= 0 || page > Constant.MAX_PAGE ? 1:page;
//      1.获取分类为life的文章
        PageHelper.startPage(page,8);
        List<CmsContents> contents = contentsService.getContentsByCategory("life");
        PageInfo pageInfo = new PageInfo(contents);

        category();
        request.getSession().setAttribute("categoriesf",cache.get("categoriesf"));
//        3.获取所有type为tag的metas对象（用作jsp页面右侧的热门标签）
        if (cache.get("tagsf")==null){
            List<MetaDto> tags = visitorService.getMetas("tag");
            cache.set("tagsf",tags);
        }
        request.getSession().setAttribute("tagsf",cache.get("tagsf"));
        //4.推荐文章
        if (cache.get("realTop")==null){
            List<CmsContents> hotArticle = contentsService.getHotArticle(Type_C.HOT_ARTICLE,6);
//
//            if (hotArticle.size()>0)
//                cache.set("realTop",hotArticle.get(0));
            cache.set("recommendArticle",hotArticle);
        }
        request.getSession().setAttribute("articles",pageInfo);
        request.getSession().setAttribute("recommendArticle",cache.get("recommendArticle"));
//        request.getSession().setAttribute("realTop",cache.get("realTop"));
//
//        request.getSession().setAttribute("is_home",true);
//        request.getSession().setAttribute("page_prefix","/page");
        saveLog(request,LogActions.LIFEPAGE,null);
        return "themes/life";
    }

    private void category() {
        if (cache.get("categoriesf")==null) {
            //2.带个某个分类，分类slug，分类description，个数的list
            List<MetaDto> categories = visitorService.getMetas("category");
            for (int i = 0; i < categories.size(); i++) {
//                去除description为private的（私密文章）
                if (categories.get(i).getDescription() != null && categories.get(i).getDescription().equals("private")) {
                    categories.remove(i);
                    i--;
                }
            }
            cache.set("categoriesf", categories);
        }
    }

    //  去往文章分类页面
    @RequestMapping(value = "/category/{keyword}",method = RequestMethod.GET)
    public String categories(@PathVariable("keyword") String keyword, HttpSession session, HttpServletRequest request){
        return categories(keyword,session,1,20,request);
    }

    @Autowired
    MetasService metasService;
    @RequestMapping(value = "/category/{keyword}/{page}")
    private String categories(@PathVariable(value = "keyword") String keyword, HttpSession session, int page, int limit, HttpServletRequest request) {

        page = (page<=0 || page>Constant.MAX_PAGE) ? 1 : page;

        MetaDto metaDto = metasService.getMeta("category",keyword);
        if (metaDto==null){
            return "common/error_404.jsp";
        }
        PageInfo articles = contentsService.getArticlesByMetaDto(metaDto.getMid(),metaDto.getCount(),page,limit);
        session.setAttribute("articles",articles);
        session.setAttribute("type", "分类");
        session.setAttribute("keyword", keyword);
        session.setAttribute("is_category",true);
//        session.setAttribute("page_prefix","/category/"+ keyword);
        saveLog(request,LogActions.INDEX_CATEGORY,keyword);
        return "themes/page-category";
    }

//    去往热门标签页面
    @RequestMapping(value = "tag/{keyword}",method = RequestMethod.GET)
    public String tags(@PathVariable("keyword") String keyword, HttpSession session, HttpServletRequest request){

        return tags(keyword,session,1,20,request);

    }

    @RequestMapping(value = {"tag/{keyword}/{page}","tag/{keyword}/{page}.html"})
    private String tags(@PathVariable(value = "keyword") String keyword, HttpSession session, int page, int limit, HttpServletRequest request) {

        page = (page<=0 || page>Constant.MAX_PAGE) ? 1 : page;

        MetaDto metaDto = metasService.getMeta("tag",keyword);
        if (metaDto==null){
            return "common/error_404.jsp";
        }
        PageInfo articles = contentsService.getArticlesByMetaDto(metaDto.getMid(),metaDto.getCount(),page,limit);
        session.setAttribute("articles",articles);
        session.setAttribute("type", "标签");
        session.setAttribute("keyword", keyword);
        session.setAttribute("is_tag",true);
//        session.setAttribute("page_prefix","/tag/"+keyword);
        saveLog(request,LogActions.INDEX_TAG,keyword);
        return "themes/page-category";


    }

    @RequestMapping(value = "/diary",method = RequestMethod.GET)
    public String diary(@RequestParam(value = "comPage",defaultValue = "1")int comPage ,HttpServletRequest request){
        CmsContents article = visitorService.getArticles("diary");
        PageHelper.startPage(comPage,20);
        List<Comment> commentslist = CommentsService.getCommentsByContents(article);
        PageInfo comments = new PageInfo(commentslist);
//        3.更新文章点击量
        updateArticleHit(article);
        request.getSession().setAttribute("article", article);
        request.setAttribute("comments" ,comments);
        saveLog(request,"查看日志页面",null);
        return "themes/diary";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(HttpServletRequest request){
        saveLog(request,"查看关于页面",null);
        return "themes/about";
    }

    /**
     * 搜索功能
     * @param keyword
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "search/{keyword}", method = RequestMethod.GET)
    public String search(@PathVariable("keyword") String keyword,
                         HttpSession session,HttpServletRequest request){
        return this.search(keyword,session,1,20,request);
    }

    @RequestMapping(value = {"search/{keyword}/{page}","search/{keyword}/{page}.html"})
    private String search(@PathVariable(value = "keyword") String keyword, HttpSession session, int page, int limit, HttpServletRequest request) {

        page = (page<=0 || page>Constant.MAX_PAGE) ? 1 : page;
//        CmsContents cmsContents = new CmsContents();
//        cmsContents.setTitle(keyword);
//        cmsContents.setStatus(Type_C.PUBLISH);
////        提出开发日志
//        cmsContents.setAllowFeed(0);
        PageInfo articles = contentsService.searchArticle(keyword,page,limit);
        session.setAttribute("articles",articles);
        session.setAttribute("type", "搜索");
        session.setAttribute("keyword", keyword);
        session.setAttribute("page_prefix","/search/"+keyword);
        saveLog(request,LogActions.INDEX_SEARCH,keyword);
        return "themes/page-category";




    }

    /**
     * 进入登录界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request){
//        看session中有无login_user参数，即是否已经登录过
        Object object = request.getSession().getAttribute(Type_C.LOGIN_SESSION_KEY);
        SysUsers user = object == null ? null : (SysUsers)object;
        if(user == null){
//            登录页面
            return "admin/login";
        }else{
//            控制台
            return "admin/console";
        }
    }

    /**
     * 转发到文章详情页面
     * @param keyOrSlug
     * @return
     */
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private CommentsService CommentsService;
    @RequestMapping(value = "article/{keyOrSlug}", method = RequestMethod.GET)
    public String toArticle(@PathVariable("keyOrSlug") String keyOrSlug, @RequestParam(value = "comPage",defaultValue = "1")int comPage, HttpServletRequest request){
//        1.获取文章对象
        CmsContents article = visitorService.getArticles(keyOrSlug);
//        判断是否有发表该文章
        if(article == null || article.getStatus().equals("draft")){
//          返回404错误页面
            return "common/error_404.jsp";
        }
//        2.获取文章评论
        comPage = comPage <= 0 || comPage > Constant.MAX_PAGE ? 1:comPage;
//        评论区的页码条显示有点问题的
        PageHelper.startPage(comPage,20);
        List<Comment> commentslist = CommentsService.getCommentsByContents(article);
        PageInfo comments = new PageInfo(commentslist);
//        3.更新文章点击量
        updateArticleHit(article);
//        4.设置参数，方便页面调用
        request.getSession().setAttribute("article", article);
//        5.转发到页面
        request.setAttribute("comments",comments);
        saveLog(request, LogActions.INDEX_VIEW,article.getTitle());
        return "themes/articles";
    }

//    更新文章的点击量
    private void updateArticleHit(CmsContents article) {
        if(article != null){
            contentsService.updateHit(article);
        }
    }

}
