package com.interzonedev.herokusupport.data.migration.result;

public class MigrationResult {
    private Object result;

    public MigrationResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        String output = "null";

        if (null != result) {
            output = result.toString();
        }

        return output;
    }
}
