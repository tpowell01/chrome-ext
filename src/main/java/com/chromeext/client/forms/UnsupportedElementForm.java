package com.chromeext.client.forms;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Andrew Kharchenko
 */
public class UnsupportedElementForm extends BaseForm {

    public UnsupportedElementForm() {
        Label lbl = new Label("This element not yet supported");
        VerticalPanel vp = new VerticalPanel();
        vp.add(lbl);
        setWidget(vp);
    }
}
