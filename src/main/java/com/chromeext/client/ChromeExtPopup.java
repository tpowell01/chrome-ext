package com.chromeext.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Andrew Kharchenko
 */
public class ChromeExtPopup extends DialogBox {

    @UiField Label lblState;
    @UiField Button btnClear;
    @UiField Button btnReplay;
    @UiField Button btnSave;

    private static ChromeExtPopupUiBinder uiBinder = GWT.create(ChromeExtPopupUiBinder.class);

    public ChromeExtPopup() {
        uiBinder.createAndBindUi(this);
    }

    @UiTemplate("ChromeExt.ui.xml")
    interface ChromeExtPopupUiBinder extends UiBinder<Widget, ChromeExtPopup> {}

    @UiFactory
    DialogBox thatsJustMe() {
        return this;
    }
}
