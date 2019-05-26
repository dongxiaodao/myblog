package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.CmsContentsExample;
import com.dongxiaodao.blog.basis.dao.CmsAttachMapper;
import com.dongxiaodao.blog.basis.dao.CmsCommentsMapper;
import com.dongxiaodao.blog.basis.dao.CmsContentsMapper;
import com.dongxiaodao.blog.basis.dto.Archive;
import com.dongxiaodao.blog.basis.bean.CmsContents;
import com.dongxiaodao.blog.basis.dao.VisitorMapper;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import com.dongxiaodao.blog.basis.dto.Statistics;
import com.dongxiaodao.blog.basis.util.MapCache;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.StartDocument;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**若业务功能很复杂的情况下，可以visitorService为一个接口，再另外定义实现类
 * @author dongxiaodao
 * @date 2019/4/8 - 9:48
 */
@Service
public class VisitorService {
    @Autowired
    VisitorMapper visitorMapper;
    @Autowired
    CmsContentsMapper cmsContentsMapper;
    @Autowired
    CmsCommentsMapper cmsCommentsMapper;
    public CmsContents getArticles(String keyOrSlug) {
        return visitorMapper.selectArticleByKeyOrSlug(keyOrSlug);
    }

//    获取按发表时间年月的文章归档信息
    public List<Archive> getArchives(){
        List<Archive> archives = visitorMapper.getArchives();
        for(Archive archive : archives){
//            先把dateTimeStr解析为日期，然后按照月份查询cmscontent对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
            String dateTimeStr = archive.getDateTimeStr();
            Date start= null;
            try {
                 start = sdf.parse(dateTimeStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(Calendar.MONTH,1);
            Date end = calendar.getTime();
            CmsContentsExample example = new CmsContentsExample();
            CmsContentsExample.Criteria criteria = example.createCriteria();
            criteria.andTypeEqualTo(Type_C.POST);
            criteria.andStatusEqualTo(Type_C.PUBLISH);
            criteria.andCreatedBetween(start,end);
//            剔除开发日志
            criteria.andAllowFeedEqualTo(0);
            example.setOrderByClause("created desc");
            List<CmsContents> contents = cmsContentsMapper.selectByExample(example);

            archive.setArticles(contents);

        }
        return archives;
    }
//  获取属于目录的标签信息
    public List<MetaDto> getMetas(String category) {
        return visitorMapper.selectMetaByTypeJoinArticle(category);
    }

    MapCache mapCache = MapCache.single();
//    获取后台显示的评论数量，文章数量等数据停机信息
    @Autowired
    CmsAttachMapper attachMapper;
    public Statistics getStatistic() {
        Statistics statistics = mapCache.get(Type_C.C_STATISTICS);
        if (statistics!=null){
            return statistics;
        }
        CmsContentsExample cmsContentsExample = new CmsContentsExample();
        CmsContentsExample.Criteria criteria = cmsContentsExample.createCriteria();
        criteria.andTypeEqualTo(Type_C.POST);
        criteria.andStatusEqualTo(Type_C.PUBLISH);
        int articleNum = (int)cmsContentsMapper.countByExample(cmsContentsExample);
        int commentNum = (int)cmsCommentsMapper.countByExample(null);
        int attachNum = (int)attachMapper.countByExample(null);
        statistics = new Statistics(articleNum,commentNum,attachNum);
        return statistics;
    }
}
