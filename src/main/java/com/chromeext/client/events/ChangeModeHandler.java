package com.chromeext.client.events;

import com.chromeext.client.model.Mode;
import com.chromeext.client.model.TargetModel;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author Andrew Kharchenko
 */
public interface ChangeModeHandler extends EventHandler {
    void onModeChanged(Mode mode, TargetModel target);
}
