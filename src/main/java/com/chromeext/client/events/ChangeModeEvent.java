package com.chromeext.client.events;

import com.chromeext.client.Mode;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.web.bindery.event.shared.Event;

/**
 * @author Andrew Kharchenko
 */
public class ChangeModeEvent extends Event {

    public static Type TYPE = new Type<ChangeModeEvent>();

    private Mode mode;
    private JavaScriptObject element;

    public ChangeModeEvent(Mode mode, JavaScriptObject element) {
        this.mode = mode;
        this.element = element;
    }

    @Override
    public Type getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Object handler) {
        ((ChangeModeHander)handler).onModeChanged(mode, element);
    }
}
