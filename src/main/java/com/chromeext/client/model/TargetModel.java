package com.chromeext.client.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Andrew Kharchenko
 */
public class TargetModel {
    private JavaScriptObject target;
    private TargetType type;

    public TargetModel(JavaScriptObject target, TargetType type) {
        this.target = target;
        this.type = type;
    }

    public JavaScriptObject getTarget() {
        return target;
    }

    public TargetType getType() {
        return type;
    }
}
