package com.chromeext.client.events;

import com.chromeext.client.model.CallResult;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author Andrew Kharchenko
 */
public interface APICallHandler extends EventHandler {
    void onAPICall(CallResult result);
}
