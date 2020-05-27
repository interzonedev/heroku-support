package com.interzonedev.herokusupport.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SpringContextUtilsIT {

    private static final Logger log = LoggerFactory.getLogger(SpringContextUtilsIT.class);

    private String[] configLocations1 = new String[]{"/com/interzonedev/herokusupport/spring/applicationContext1.xml"};

    private String[] configLocations2 = new String[]{"/com/interzonedev/herokusupport/spring/applicationContext2.xml"};

    private String[] configLocationsBoth = new String[]{
            "/com/interzonedev/herokusupport/spring/applicationContext1.xml",
            "/com/interzonedev/herokusupport/spring/applicationContext2.xml"};

    @Test
    public void testGetApplicationContextNullConfigLocations() {
        log.debug("testGetApplicationContextNullConfigLocations: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getApplicationContext(null));

        log.debug("testGetApplicationContextNullConfigLocations: End");
    }

    @Test
    public void testGetApplicationContextEmptyConfigLocations() {
        log.debug("testGetApplicationContextEmptyConfigLocations: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getApplicationContext(new String[]{}));

        log.debug("testGetApplicationContextEmptyConfigLocations: End");
    }

    @Test
    public void testGetApplicationContextMultiple() {
        log.debug("testGetApplicationContextMultiple: Start");

        ApplicationContext applicationContext1 = SpringContextUtils.getApplicationContext(configLocations1);
        ApplicationContext applicationContext1Again = SpringContextUtils.getApplicationContext(configLocations1);

        assertNotNull(applicationContext1);
        assertNotNull(applicationContext1Again);
        assertEquals(applicationContext1, applicationContext1Again);
        assertEquals(1, applicationContext1.getBeanDefinitionCount());
        assertTrue(applicationContext1.containsBean("testBean1"));

        TestBean testBean11 = (TestBean) applicationContext1.getBean("testBean1");
        boolean exception1Thrown = false;
        try {
            applicationContext1.getBean("testBean2");
            fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception1Thrown = true;
        }

        assertNotNull(testBean11);
        assertTrue(exception1Thrown);
        assertEquals("value1", testBean11.getValue());

        ApplicationContext applicationContext2 = SpringContextUtils.getApplicationContext(configLocations2);
        ApplicationContext applicationContext2Again = SpringContextUtils.getApplicationContext(configLocations2);

        assertNotNull(applicationContext2);
        assertNotNull(applicationContext2Again);
        assertEquals(applicationContext2, applicationContext2Again);
        assertEquals(1, applicationContext2.getBeanDefinitionCount());
        assertTrue(applicationContext2.containsBean("testBean2"));

        TestBean testBean22 = (TestBean) applicationContext2.getBean("testBean2");
        boolean exception2Thrown = false;
        try {
            applicationContext2.getBean("testBean1");
            fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception2Thrown = true;
        }

        assertNotNull(testBean22);
        assertTrue(exception2Thrown);
        assertEquals("value2", testBean22.getValue());

        ApplicationContext applicationContextBoth = SpringContextUtils.getApplicationContext(configLocationsBoth);
        ApplicationContext applicationContextBothAgain = SpringContextUtils.getApplicationContext(configLocationsBoth);

        assertNotNull(applicationContextBoth);
        assertNotNull(applicationContextBothAgain);
        assertEquals(applicationContextBoth, applicationContextBothAgain);
        assertEquals(2, applicationContextBoth.getBeanDefinitionCount());
        assertTrue(applicationContextBoth.containsBean("testBean1"));
        assertTrue(applicationContextBoth.containsBean("testBean2"));

        TestBean testBeanBoth1 = (TestBean) applicationContextBoth.getBean("testBean1");
        TestBean testBeanBoth2 = (TestBean) applicationContextBoth.getBean("testBean2");

        assertNotNull(testBeanBoth1);
        assertNotNull(testBeanBoth2);
        assertEquals("value1", testBeanBoth1.getValue());
        assertEquals("value2", testBeanBoth2.getValue());

        assertNotSame(applicationContext1, applicationContext2);
        assertNotSame(applicationContext1, applicationContextBoth);
        assertNotSame(applicationContext2, applicationContextBoth);

        assertNotSame(testBean11, testBeanBoth1);
        assertNotSame(testBean22, testBeanBoth2);

        assertEquals(testBean11, testBeanBoth1);
        assertEquals(testBean22, testBeanBoth2);

        log.debug("testGetApplicationContextMultiple: End");
    }

    @Test
    public void testGetBeanNullConfigLocations() {
        log.debug("testGetBeanNullConfigLocations: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getBean(null, "test"));

        log.debug("testGetBeanNullConfigLocations: End");
    }

    @Test
    public void testGetBeanEmptyConfigLocations() {
        log.debug("testGetBeanEmptyConfigLocations: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getBean(new String[]{}, "test"));

        log.debug("testGetBeanEmptyConfigLocations: End");
    }

    @Test
    public void testGetBeanNullBeanName() {
        log.debug("testGetBeanNullBeanName: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getBean(configLocations1, null));

        log.debug("testGetBeanNullBeanName: End");
    }

    @Test
    public void testGetBeanEmptyBeanName() {
        log.debug("testGetBeanEmptyBeanName: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getBean(configLocations1, ""));

        log.debug("testGetBeanEmptyBeanName: End");
    }

    @Test
    public void testGetBeanBlankBeanName() {
        log.debug("testGetBeanBlankBeanName: Start");

        assertThrows(IllegalArgumentException.class, () -> SpringContextUtils.getBean(configLocations1, " "));

        log.debug("testGetBeanBlankBeanName: End");
    }

    @Test
    public void testGetBeanMultiple() {
        log.debug("testGetBeanMultiple: Start");

        TestBean testBean11 = (TestBean) SpringContextUtils.getBean(configLocations1, "testBean1");
        boolean exception1Thrown = false;
        try {
            SpringContextUtils.getBean(configLocations1, "testBean2");
            fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception1Thrown = true;
        }

        assertNotNull(testBean11);
        assertTrue(exception1Thrown);
        assertEquals("value1", testBean11.getValue());

        TestBean testBean22 = (TestBean) SpringContextUtils.getBean(configLocations2, "testBean2");
        boolean exception2Thrown = false;
        try {
            SpringContextUtils.getBean(configLocations2, "testBean1");
            fail();
        } catch (NoSuchBeanDefinitionException nsbde) {
            exception2Thrown = true;
        }

        assertNotNull(testBean22);
        assertTrue(exception2Thrown);
        assertEquals("value2", testBean22.getValue());

        TestBean testBeanBoth1 = (TestBean) SpringContextUtils.getBean(configLocationsBoth, "testBean1");
        TestBean testBeanBoth2 = (TestBean) SpringContextUtils.getBean(configLocationsBoth, "testBean2");

        assertNotNull(testBeanBoth1);
        assertNotNull(testBeanBoth2);
        assertEquals("value1", testBeanBoth1.getValue());
        assertEquals("value2", testBeanBoth2.getValue());

        assertNotSame(testBean11, testBeanBoth1);
        assertNotSame(testBean22, testBeanBoth2);

        assertEquals(testBean11, testBeanBoth1);
        assertEquals(testBean22, testBeanBoth2);

        log.debug("testGetBeanMultiple: End");
    }
}
