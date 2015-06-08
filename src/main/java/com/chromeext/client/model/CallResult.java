package com.chromeext.client.model;

/**
 * @author Andrew Kharchenko
 */
public class CallResult {

    private boolean error = false;

    private String response;

    public CallResult(String response) {
        this.response = response;
    }

    public CallResult(boolean error, String response) {
        this.error = error;
        this.response = response;
    }

    public boolean isError() {
        return error;
    }

    public String getResponse() {
        return response;
    }
}
