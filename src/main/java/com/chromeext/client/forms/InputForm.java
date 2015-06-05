package com.chromeext.client.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Form for section B for input HTML elements (input, textarea, select).
 *
 * @author Andrew Kharchenko
 */
public class InputForm extends BaseForm {

    @UiField ListBox lbSelectInput;
    @UiField TextBox tbCustomInput;
    @UiField CheckBox cbSubmitForm;
    @UiField Button btnSave;

    private static InputFormUiBinder uiBinder = GWT.create(InputFormUiBinder.class);

    @UiTemplate("InputForm.ui.xml")
    interface InputFormUiBinder extends UiBinder<Widget, InputForm> {}

    public InputForm() {
        uiBinder.createAndBindUi(this);

        lbSelectInput.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                String selectedValue = lbSelectInput.getSelectedValue();
                boolean enable = "3".equals(selectedValue);
                tbCustomInput.setEnabled(enable);
                if (enable) {
                    tbCustomInput.setFocus(true);
                }
            }
        });

    }

    @UiFactory
    public BaseForm me() {
        return this;
    }
}
