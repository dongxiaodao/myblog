package com.dongxiaodao.blog.basis.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


//通过ApplicationContext的getBean方法来获取Spring容器中已初始化的bean


/**
 * @author dongxiaodao
 * @date 2019/4/7 - 20:27
 */
//@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
    //把这个类纳入进spring容器中管理

//    实现该接口的setApplicationContext(ApplicationContext context)方法，
//    并保存ApplicationContext 对象。Spring初始化时，会通过该方法将ApplicationContext对象注入。
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境


    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /*
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     * 通过传递applicationContext参数初始化成员变量applicationContext
     */

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }
}
