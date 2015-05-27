package com.chromeext.client.events;

import com.chromeext.client.Mode;
import com.google.web.bindery.event.shared.Event;

/**
 * @author Andrew Kharchenko
 */
public class ChangeModeEvent extends Event {

    public static Type TYPE = new Type<ChangeModeEvent>();

    private Mode mode;

    public ChangeModeEvent(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Type getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Object handler) {
        ((ChangeModeHander)handler).onModeChanged(mode);
    }
}
