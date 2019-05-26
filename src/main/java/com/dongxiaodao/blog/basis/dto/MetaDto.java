package com.dongxiaodao.blog.basis.dto;

import com.dongxiaodao.blog.basis.bean.CmsMetas;

/**
 * @author dongxiaodao
 * @date 2019/4/17 - 9:12
 */
public class MetaDto extends CmsMetas{
    int count;

    public int getCount(){
        return count;
    }

    @Override
    public String toString() {
        return super.toString()+"COUNT=" + count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
