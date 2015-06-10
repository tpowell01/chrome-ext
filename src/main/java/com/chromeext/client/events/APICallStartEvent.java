package com.chromeext.client.events;

import com.google.web.bindery.event.shared.Event;

/**
 * Event fired to notify all subscribed parties about start of API call
 *
 * @author Andrew Kharchenko
 */
public class APICallStartEvent extends Event<APICallStartHandler> {

    public static Type<APICallStartHandler> TYPE = new Type<>();

    @Override
    public Type<APICallStartHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(APICallStartHandler handler) {
        handler.onAPICallStart();
    }
}
