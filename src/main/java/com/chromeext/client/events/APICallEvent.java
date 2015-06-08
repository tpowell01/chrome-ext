package com.chromeext.client.events;

import com.chromeext.client.model.CallResult;
import com.google.web.bindery.event.shared.Event;

/**
 * @author Andrew Kharchenko
 */
public class APICallEvent extends Event<APICallHandler> {

    public static Type<APICallHandler> TYPE = new Type<>();

    private CallResult result;

    public APICallEvent(CallResult result) {
        this.result = result;
    }

    @Override
    public Type<APICallHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(APICallHandler handler) {
        handler.onAPICall(result);
    }
}
