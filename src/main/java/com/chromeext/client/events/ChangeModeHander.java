package com.chromeext.client.events;

import com.chromeext.client.Mode;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author Andrew Kharchenko
 */
public interface ChangeModeHander extends EventHandler {
    void onModeChanged(Mode mode, JavaScriptObject element);
}
