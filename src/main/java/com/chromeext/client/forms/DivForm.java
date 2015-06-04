package com.chromeext.client.forms;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Andrew Kharchenko
 */
public class DivForm extends BaseForm {

    public DivForm(String innerHTML) {
        VerticalPanel vp = new VerticalPanel();

        Label lblContents = new Label("Contents");
        vp.add(lblContents);

        TextArea taContents = new TextArea();
        if (innerHTML != null && !innerHTML.isEmpty()) {
            taContents.setText(innerHTML);
        }
        vp.add(taContents);

        CheckBox cbParseAddress = new CheckBox("Parse Address");
        vp.add(cbParseAddress);

        Button btnSave = new Button("Save");
        vp.add(btnSave);

        setWidget(vp);
    }
}
