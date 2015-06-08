package com.chromeext.client.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrew Kharchenko
 */
public class ContextErrorForm extends BaseForm {

    @UiField Label lblErrorText;

    private static ContextErrorFormUiBinder uiBinder = GWT.create(ContextErrorFormUiBinder.class);


    public ContextErrorForm(String error) {
        uiBinder.createAndBindUi(this);
        lblErrorText.setText(error);
    }

    @UiTemplate("ContextErrorForm.ui.xml")
    interface ContextErrorFormUiBinder extends UiBinder<Widget, ContextErrorForm>{}

    @UiFactory
    public BaseForm me() {
        return this;
    }
}
