package com.interzonedev.herokusupport.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringContextUtilsIT {

    private Logger log = (Logger) LoggerFactory.getLogger(getClass());

    private String[] configLocations1 = new String[] { "/com/interzonedev/herokusupport/spring/applicationContext1.xml" };

    private String[] configLocations2 = new String[] { "/com/interzonedev/herokusupport/spring/applicationContext2.xml" };

    private String[] configLocationsBoth = new String[] {
            "/com/interzonedev/herokusupport/spring/applicationContext1.xml",
            "/com/interzonedev/herokusupport/spring/applicationContext2.xml" };

    @Test(expected = IllegalArgumentException.class)
    public void testGetApplicationContextNullConfigLocations() {

        log.debug("testGetApplicationContextNullConfigLocations");

        SpringContextUtils.getApplicationContext(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetApplicationContextEmptyConfigLocations() {

        log.debug("testGetApplicationContextEmptyConfigLocations");

        SpringContextUtils.getApplicationContext(new String[] {});

    }

    @Test
    public void testGetApplicationContextMultiple() {

        log.debug("testGetApplicationContextMultiple");

        ApplicationContext applicationContext1 = SpringContextUtils.getApplicationContext(configLocations1);
        ApplicationContext applicationContext1Again = SpringContextUtils.getApplicationContext(configLocations1);

        Assert.assertNotNull(applicationContext1);
        Assert.assertNotNull(applicationContext1Again);
        Assert.assertEquals(applicationContext1, applicationContext1Again);
        Assert.assertEquals(1, applicationContext1.getBeanDefinitionCount());
        Assert.assertTrue(applicationContext1.containsBean("testBean1"));

        TestBean testBean11 = (TestBean) applicationContext1.getBean("testBean1");
        boolean exception1Thrown = false;
        try {
            applicationContext1.getBean("testBean2");
            Assert.fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception1Thrown = true;
        }

        Assert.assertNotNull(testBean11);
        Assert.assertTrue(exception1Thrown);
        Assert.assertEquals("value1", testBean11.getValue());

        ApplicationContext applicationContext2 = SpringContextUtils.getApplicationContext(configLocations2);
        ApplicationContext applicationContext2Again = SpringContextUtils.getApplicationContext(configLocations2);

        Assert.assertNotNull(applicationContext2);
        Assert.assertNotNull(applicationContext2Again);
        Assert.assertEquals(applicationContext2, applicationContext2Again);
        Assert.assertEquals(1, applicationContext2.getBeanDefinitionCount());
        Assert.assertTrue(applicationContext2.containsBean("testBean2"));

        TestBean testBean22 = (TestBean) applicationContext2.getBean("testBean2");
        boolean exception2Thrown = false;
        try {
            applicationContext2.getBean("testBean1");
            Assert.fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception2Thrown = true;
        }

        Assert.assertNotNull(testBean22);
        Assert.assertTrue(exception2Thrown);
        Assert.assertEquals("value2", testBean22.getValue());

        ApplicationContext applicationContextBoth = SpringContextUtils.getApplicationContext(configLocationsBoth);
        ApplicationContext applicationContextBothAgain = SpringContextUtils.getApplicationContext(configLocationsBoth);

        Assert.assertNotNull(applicationContextBoth);
        Assert.assertNotNull(applicationContextBothAgain);
        Assert.assertEquals(applicationContextBoth, applicationContextBothAgain);
        Assert.assertEquals(2, applicationContextBoth.getBeanDefinitionCount());
        Assert.assertTrue(applicationContextBoth.containsBean("testBean1"));
        Assert.assertTrue(applicationContextBoth.containsBean("testBean2"));

        TestBean testBeanBoth1 = (TestBean) applicationContextBoth.getBean("testBean1");
        TestBean testBeanBoth2 = (TestBean) applicationContextBoth.getBean("testBean2");

        Assert.assertNotNull(testBeanBoth1);
        Assert.assertNotNull(testBeanBoth2);
        Assert.assertEquals("value1", testBeanBoth1.getValue());
        Assert.assertEquals("value2", testBeanBoth2.getValue());

        Assert.assertNotSame(applicationContext1, applicationContext2);
        Assert.assertNotSame(applicationContext1, applicationContextBoth);
        Assert.assertNotSame(applicationContext2, applicationContextBoth);

        Assert.assertNotSame(testBean11, testBeanBoth1);
        Assert.assertNotSame(testBean22, testBeanBoth2);

        Assert.assertEquals(testBean11, testBeanBoth1);
        Assert.assertEquals(testBean22, testBeanBoth2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeanNullConfigLocations() {

        log.debug("testGetBeanNullConfigLocations");

        SpringContextUtils.getBean(null, "test");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeanEmptyConfigLocations() {

        log.debug("testGetBeanEmptyConfigLocations");

        SpringContextUtils.getBean(new String[] {}, "test");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeanNullBeanName() {

        log.debug("testGetBeanNullBeanName");

        SpringContextUtils.getBean(configLocations1, null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeanEmptyBeanName() {

        log.debug("testGetBeanEmptyBeanName");

        SpringContextUtils.getBean(configLocations1, "");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBeanBlankBeanName() {

        log.debug("testGetBeanBlankBeanName");

        SpringContextUtils.getBean(configLocations1, " ");

    }

    @Test
    public void testGetBeanMultiple() {

        log.debug("testGetBeanMultiple");

        TestBean testBean11 = (TestBean) SpringContextUtils.getBean(configLocations1, "testBean1");
        boolean exception1Thrown = false;
        try {
            SpringContextUtils.getBean(configLocations1, "testBean2");
            Assert.fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception1Thrown = true;
        }

        Assert.assertNotNull(testBean11);
        Assert.assertTrue(exception1Thrown);
        Assert.assertEquals("value1", testBean11.getValue());

        TestBean testBean22 = (TestBean) SpringContextUtils.getBean(configLocations2, "testBean2");
        boolean exception2Thrown = false;
        try {
            SpringContextUtils.getBean(configLocations2, "testBean1");
            Assert.fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception2Thrown = true;
        }

        Assert.assertNotNull(testBean22);
        Assert.assertTrue(exception2Thrown);
        Assert.assertEquals("value2", testBean22.getValue());

        TestBean testBeanBoth1 = (TestBean) SpringContextUtils.getBean(configLocationsBoth, "testBean1");
        TestBean testBeanBoth2 = (TestBean) SpringContextUtils.getBean(configLocationsBoth, "testBean2");

        Assert.assertNotNull(testBeanBoth1);
        Assert.assertNotNull(testBeanBoth2);
        Assert.assertEquals("value1", testBeanBoth1.getValue());
        Assert.assertEquals("value2", testBeanBoth2.getValue());

        Assert.assertNotSame(testBean11, testBeanBoth1);
        Assert.assertNotSame(testBean22, testBeanBoth2);

        Assert.assertEquals(testBean11, testBeanBoth1);
        Assert.assertEquals(testBean22, testBeanBoth2);
    }
}
