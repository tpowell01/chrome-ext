package com.chromeext.client.events;

import com.chromeext.client.model.Mode;
import com.chromeext.client.model.TargetModel;
import com.google.web.bindery.event.shared.Event;

/**
 * @author Andrew Kharchenko
 */
public class ChangeModeEvent extends Event {

    public static Type TYPE = new Type<ChangeModeEvent>();

    private Mode mode;
    private TargetModel target;

    public ChangeModeEvent(Mode mode, TargetModel element) {
        this.mode = mode;
        this.target = element;
    }

    @Override
    public Type getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(Object handler) {
        ((ChangeModeHander)handler).onModeChanged(mode, target);
    }
}
