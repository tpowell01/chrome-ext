package com.chromeext.client.events;

import com.google.web.bindery.event.shared.Event;

/**
 * Event used to notify all parties that API call was ended.
 * @author Andrew Kharchenko
 */
public class APICallEndEvent extends Event<APICallEndHandler> {

    public static Type<APICallEndHandler> TYPE = new Type<>();

    @Override
    public Type<APICallEndHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(APICallEndHandler handler) {
        handler.onAPICallEnd();
    }
}
