package com.chromeext.client.events;

import com.chromeext.client.model.Mode;
import com.chromeext.client.model.TargetModel;
import com.google.web.bindery.event.shared.Event;

/**
 * @author Andrew Kharchenko
 */
public class ChangeModeEvent extends Event<ChangeModeHandler> {

    public static Type<ChangeModeHandler> TYPE = new Type<>();

    private Mode mode;
    private TargetModel target;

    public ChangeModeEvent(Mode mode, TargetModel element) {
        this.mode = mode;
        this.target = element;
    }

    @Override
    public Type<ChangeModeHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangeModeHandler handler) {
        handler.onModeChanged(mode, target);
    }

}
