package com.interzonedev.herokusupport.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TestBean {

    private String value;

    public TestBean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("value", getValue()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getValue()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TestBean)) {
            return false;
        }

        TestBean that = (TestBean) obj;

        boolean equals = new EqualsBuilder().append(getValue(), that.getValue()).isEquals();

        return equals;
    }

}
