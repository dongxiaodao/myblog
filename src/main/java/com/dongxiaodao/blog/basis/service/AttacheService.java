package com.dongxiaodao.blog.basis.service;

import com.dongxiaodao.blog.basis.bean.CmsAttachExample;
import com.dongxiaodao.blog.basis.dao.CmsAttachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongxiaodao
 * @date 2019/4/18 - 10:02
 */
@Service
public class AttacheService {

    @Autowired
    CmsAttachMapper attachMapper;
    public void deleteAttach(String thumbImg) {
        CmsAttachExample example = new CmsAttachExample();
        CmsAttachExample.Criteria criteria = example.createCriteria();
        criteria.andFkeyEqualTo(thumbImg);
        int num = (int)attachMapper.countByExample(example);
        if (num>0){
            attachMapper.deleteByExample(example);
        }
    }
}
