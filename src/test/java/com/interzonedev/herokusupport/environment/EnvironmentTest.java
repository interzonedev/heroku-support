package com.interzonedev.herokusupport.environment;

import org.junit.Assert;
import org.junit.Test;

public class EnvironmentTest {

    @Test
    public void testLocal() {
        Assert.assertEquals(Environment.LOCAL, Environment.getCurrentEnvironment());
    }

}
