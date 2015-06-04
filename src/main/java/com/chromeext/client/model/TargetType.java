package com.chromeext.client.model;

/**
 * @author Andrew Kharchenko
 */
public enum TargetType {
    UNSUPPORTED(0),
    //for form elements like input, select etc.
    INPUT(1),
    //for static elements like div, span, p etc.
    DIV(2);

    private int type;

    TargetType(int type) {
        this.type = type;
    }

    public static TargetType getByType(int type) {
        TargetType[] values = values();
        for (TargetType tt : values) {
            if (tt.type == type) {
                return tt;
            }
        }
        return null;
    }
}
