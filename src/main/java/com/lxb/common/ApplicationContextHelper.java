package com.lxb.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static <T> T popBean(Class<T> tClass) {
        if (applicationContext == null) {
            return null;
        } else {
            return applicationContext.getBean(tClass);
        }
    }

    public static <T> T popBean(String name, Class<T> tClass) {
        if (applicationContext == null) {
            return null;
        } else {
            return applicationContext.getBean(name, tClass);
        }
    }
}
