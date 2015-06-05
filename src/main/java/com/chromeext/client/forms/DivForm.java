package com.chromeext.client.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrew Kharchenko
 */
public class DivForm extends BaseForm {

    @UiField TextArea taContents;
    @UiField CheckBox cbParseAddress;
    @UiField Button btnSave;

    private static DivFormUiBinder uiBinder = GWT.create(DivFormUiBinder.class);

    @UiTemplate("DivForm.ui.xml")
    interface DivFormUiBinder extends UiBinder<Widget, DivForm> {}

    public DivForm(String innerHTML) {
        uiBinder.createAndBindUi(this);
        if (innerHTML != null && !innerHTML.isEmpty()) {
            taContents.setText(innerHTML);
        }
    }

    @UiFactory
    public BaseForm me() {
        return this;
    }
}
