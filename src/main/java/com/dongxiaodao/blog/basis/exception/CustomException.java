package com.dongxiaodao.blog.basis.exception;

/**
 * @author dongxiaodao
 * @date 2019/4/13 - 16:03
 */
public class CustomException extends RuntimeException{
    public CustomException(){}

    public CustomException(String msg){
        super(msg);
    }


}
