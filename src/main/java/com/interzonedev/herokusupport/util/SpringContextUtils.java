package com.interzonedev.herokusupport.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpringContextUtils {

    private static final Map<String[], ApplicationContext> applicationContexts = new HashMap<String[], ApplicationContext>();

    public static synchronized ApplicationContext getApplicationContext(String[] configLocations) {

        Assert.notNull(configLocations, "getApplicationContext: The config locations must be set");
        Assert.notEmpty(configLocations, "getApplicationContext: The config locations must not be empty");

        ApplicationContext applicationContext = null;

        for (String[] aConfigLocations : applicationContexts.keySet()) {
            if (Objects.deepEquals(configLocations, aConfigLocations)) {
                applicationContext = applicationContexts.get(aConfigLocations);
            }
        }

        if (null == applicationContext) {
            applicationContext = new ClassPathXmlApplicationContext(configLocations);
            applicationContexts.put(configLocations, applicationContext);
        }

        return applicationContext;

    }

    public static Object getBean(String[] configLocations, String beanName) {

        Assert.notNull(configLocations, "getBean: The config locations must be set");
        Assert.notEmpty(configLocations, "getBean: The config locations must not be empty");
        Assert.hasText(beanName, "getBean: The bean name must be set");

        ApplicationContext applicationContext = getApplicationContext(configLocations);
        Object bean = applicationContext.getBean(beanName);
        return bean;

    }

}
