package com.chromeext.client.model;

import com.google.gwt.json.client.JSONObject;

/**
 * @author Andrew Kharchenko
 */
public class CallResult {

    private boolean error = false;

    private String response;
    private JSONObject jsonResponse;

    public CallResult(String response) {
        this.response = response;
    }

    public CallResult(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
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

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }
}
