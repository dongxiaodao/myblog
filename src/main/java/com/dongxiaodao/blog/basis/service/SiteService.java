package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.CmsComments;
import com.dongxiaodao.blog.basis.dao.VisitorMapper;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import com.dongxiaodao.blog.basis.util.MapCache;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/4/14 - 10:23
 */
@Service
public class SiteService {

    MapCache mapCache = MapCache.single();
//    插入文章，评论之类的之后，缓存的最新文章，最近评论可能变化，所以删除对应的缓存
    public void cleanCache(String key){
        if(StringUtils.isNotBlank(key)){
//            添加功能，若传入为*,则清空缓存
            if("*".equals(key)){
                mapCache.clean();
            }else{
                mapCache.delete(key);
            }
        }
    }
    @Autowired
    VisitorMapper visitorMapper;
    public List<MetaDto> getAllMetas(String category) {
        return visitorMapper.selectAllMetaByType(category);
    }
}
