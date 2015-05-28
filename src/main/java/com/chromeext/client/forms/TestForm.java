package com.chromeext.client.forms;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Andrew Kharchenko
 */
public class TestForm extends BaseForm {

    public TestForm() {
        Label lbl = new Label("Test Form");
        Button btn = new Button("Submit");

        VerticalPanel vp = new VerticalPanel();
        vp.add(lbl);
        vp.add(btn);

        setWidget(vp);
    }
}
