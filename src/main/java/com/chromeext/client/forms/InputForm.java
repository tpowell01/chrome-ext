package com.chromeext.client.forms;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form for section B for input HTML elements (input, textarea, select).
 *
 * @author Andrew Kharchenko
 */
public class InputForm extends BaseForm {

    private ListBox lbInput;
    private TextBox tbCustomInput;

    public InputForm() {
        VerticalPanel vp = new VerticalPanel();

        Label lblSelectInput = new Label("Select Input");
        vp.add(lblSelectInput);

        lbInput = new ListBox();
        lbInput.setMultipleSelect(false);
        lbInput.addItem("Zipcodes", "0");
        lbInput.addItem("Increment", "1");
        lbInput.addItem("Category", "2");
        lbInput.addItem("Custom", "3");

        lbInput.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                String selectedValue = lbInput.getSelectedValue();
                boolean enable = "3".equals(selectedValue);
                tbCustomInput.setEnabled(enable);
                if (enable) {
                    tbCustomInput.setFocus(true);
                }
            }
        });

        vp.add(lbInput);

        Label lblCustomInput = new Label("Custom Input");
        vp.add(lblCustomInput);

        tbCustomInput = new TextBox();
        tbCustomInput.setEnabled(false);
        vp.add(tbCustomInput);

        CheckBox cbSubmitForm = new CheckBox("Submit Form");
        vp.add(cbSubmitForm);

        Button btnSave = new Button("Save");
        vp.add(btnSave);

        setWidget(vp);
    }
}
