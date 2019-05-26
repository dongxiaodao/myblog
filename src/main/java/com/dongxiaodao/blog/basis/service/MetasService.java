package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.*;
import com.dongxiaodao.blog.basis.dao.CmsContentsMapper;
import com.dongxiaodao.blog.basis.dao.CmsMetasMapper;
import com.dongxiaodao.blog.basis.dao.CmsRelationshipsMapper;
import com.dongxiaodao.blog.basis.dao.VisitorMapper;
import com.dongxiaodao.blog.basis.dto.MetaDto;
import com.dongxiaodao.blog.basis.exception.CustomException;
import com.dongxiaodao.blog.basis.util.Type_C;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dongxiaodao
 * @date 2019/4/17 - 10:16
 */
@Service
public class MetasService {
    @Autowired
    private VisitorMapper visitorMapper;
    public MetaDto getMeta(String category, String name) {
        if(StringUtils.isNotBlank(category) && StringUtils.isNotBlank(name)){
            return visitorMapper.selectMetaByTypeAndName(category,name);
        }
        return null;
    }

    @Autowired
    CmsMetasMapper cmsMetasMapper;
//    获取文章分类目录信息，提供给发表文章的下拉列表
    public List<CmsMetas> getMetas(String type) {
        CmsMetasExample example = new CmsMetasExample();
        CmsMetasExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        List<CmsMetas> metas = cmsMetasMapper.selectByExample(example);
        return metas;
    }

    public void saveMetas(String type, String name, Integer mid) {
        if(StringUtils.isNotBlank(name)){
            CmsMetas metas = visitorMapper.selectMetaByTypeAndName(type,name);
            if(null!= metas.getMid()){
                throw new CustomException("已经存在该项");
            }else{
                if(null != mid){
                    metas = new CmsMetas();
                    metas.setMid(mid);
                    metas.setName(name);
                    metas.setType(type);
                    metasMapper.updateByPrimaryKey(metas);
                }else{
                    metas = new CmsMetas();
                    metas.setType(type);
                    metas.setName(name);
                    metasMapper.insert(metas);
                }
            }
        }
    }
    @Autowired
    CmsMetasMapper metasMapper;
    @Autowired
    CmsRelationshipsMapper relationshipsMapper;
    private void saveOrUpdate(Integer cid, String name, String type) {
        CmsMetasExample example = new CmsMetasExample();
        CmsMetasExample.Criteria criteria = example.createCriteria();
        criteria.andTypeEqualTo(type);
        criteria.andNameEqualTo(name);
        List<CmsMetas> metasList = metasMapper.selectByExample(example);
        int mid = 0;
        if (metasList!=null && metasList.size()>0){
            mid = metasList.get(0).getMid();
        }else{
            CmsMetas metas = new CmsMetas();
            metas.setName(name);
            metas.setType(type);
            metas.setSort(0);
            metas.setParent(0);
            metas.setSlug(name);
            metasMapper.insert(metas);
            mid = metas.getMid();
        }
        if (mid!=0){
            CmsRelationshipsExample example1 = new CmsRelationshipsExample();
            CmsRelationshipsExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andCidEqualTo(cid);
            criteria1.andMidEqualTo(mid);
            List<CmsRelationships> relationships = relationshipsMapper.selectByExample(example1);
            if (relationships.size()==0){
                CmsRelationships cmsRelationships = new CmsRelationships();
                cmsRelationships.setCid(cid);
                cmsRelationships.setMid(mid);
                relationshipsMapper.insert(cmsRelationships);
            }

        }
    }
    @Autowired
    CmsContentsMapper contentsMapper;
    @Autowired
    ContentsService contentsService;

    public void saveMetas(Integer cid, String label, String type) {
        if (cid == null){
            throw new CustomException("文章的id不能为空");
        }
        if (StringUtils.isNotBlank(label) && StringUtils.isNotBlank(type)){
            String[] labels = StringUtils.split(label,",");
            for (String name:labels) {
                this.saveOrUpdate(cid,name,type);
            }
        }
    }

    public void delete(int mid) {
        CmsMetas metas = metasMapper.selectByPrimaryKey(mid);
        if(null!=metas){
            String type = metas.getType();
            String name = metas.getName();
            metasMapper.deleteByPrimaryKey(mid);
            CmsRelationshipsExample example = new CmsRelationshipsExample();
            CmsRelationshipsExample.Criteria criteria = example.createCriteria();
            criteria.andMidEqualTo(mid);
            List<CmsRelationships> relationshipsList = relationshipsMapper.selectByExample(example);
            if(null!=relationshipsList){
                for(CmsRelationships r:relationshipsList){
                    CmsContents contents = contentsMapper.selectByPrimaryKey(r.getCid());
                    if(null!=contents){
                        boolean isUpdate = false;
                        if(type.equals(Type_C.CATEGORY)){
                            contents.setCategories(reMeta(name,contents.getCategories()));
                            isUpdate = true;
                        }
                        if(type.equals(Type_C.TAG)){
                            contents.setTags(reMeta(name,contents.getTags()));
                            isUpdate = true;
                        }
                        if(isUpdate)
                            contentsService.update(contents);
                    }
                }
            }
            relationshipsMapper.deleteByExample(example);
        }
    }

    private String reMeta(String name, String tags) {
        String[] ms = StringUtils.split(tags, ",");
        StringBuffer sbuf = new StringBuffer();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }
}
