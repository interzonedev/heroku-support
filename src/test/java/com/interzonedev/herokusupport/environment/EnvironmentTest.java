package com.interzonedev.herokusupport.environment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvironmentTest {

    @Test
    public void testLocal() {
        assertEquals(Environment.LOCAL, Environment.getCurrentEnvironment());
    }

}
