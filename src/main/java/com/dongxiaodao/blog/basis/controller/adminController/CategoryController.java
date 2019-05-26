package com.dongxiaodao.blog.basis.controller.adminController;

import com.dongxiaodao.blog.basis.controller.BaseController;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.service.MetasService;
import com.dongxiaodao.blog.basis.service.SiteService;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by ghost 2018/3/11 11:19
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(ContentController.class);

    @Autowired
    private MetasService metasService;
    @Autowired
    private SiteService siteService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        List<MetaDto> categories = siteService.getAllMetas(Type_C.CATEGORY);
        List<MetaDto> tags = siteService.getAllMetas(Type_C.TAG);
        request.getSession().setAttribute("categories",categories);
        request.getSession().setAttribute("tags",tags);
        return "admin/category";
    }

    @ResponseBody
    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Map<String,Object> save(@RequestParam("cname") String cname, Integer mid){
        Map<String,Object> map = new HashMap<>();
        try{
            metasService.saveMetas(Type_C.CATEGORY,cname,mid);
            siteService.cleanCache(Type_C.C_STATISTICS);
        }catch (Exception e){
            String msg = "分类保存失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success", false);
            return map;
        }
        map.put("success", true);
        return map;
    }

    @ResponseBody
    @RequestMapping("delete")
    public Map<String,Object> delete(@RequestParam("mid") int mid){
        Map<String,Object> map = new HashMap<>();
        try{
            metasService.delete(mid);
            siteService.cleanCache(Type_C.C_STATISTICS);

        }catch (Exception e){
            String msg = "删除失败";
            if (e instanceof CustomException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg, e);
            }
            map.put("msg",msg);
            map.put("success", false);
            return map;
        }
        map.put("success", true);
        return map;
    }
}
