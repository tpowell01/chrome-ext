package com.chromeext.client;

import com.chromeext.client.events.ChangeModeEvent;
import com.chromeext.client.events.ChangeModeHander;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Andrew Kharchenko
 */
public class ChromeExtPopup extends DialogBox {

    @UiField Label lblState;
    @UiField Label lblStateDescription;
    @UiField Button btnClear;
    @UiField Button btnReplay;
    @UiField Button btnSave;

    HTML title = new HTML("Chrome Extension");
    HTML close = new HTML("[X]");
    HorizontalPanel captionPanel = new HorizontalPanel();

    private static ChromeExtPopupUiBinder uiBinder = GWT.create(ChromeExtPopupUiBinder.class);

    private EventBus eventBus;

    public ChromeExtPopup(EventBus eventBus) {
        this.eventBus = eventBus;
        initEvents();

        captionPanel.add(title);
        captionPanel.add(close);
        captionPanel.addStyleName("chromeext-caption");
        title.addStyleName("chromeext-title");
        close.addStyleName("chromeext-close");


        Element captionHolder = getCellElement(0, 1);

        Node firstChild = captionHolder.getFirstChild();
        firstChild.appendChild(captionPanel.getElement());

        uiBinder.createAndBindUi(this);
    }

    private void initEvents() {
        eventBus.addHandler(ChangeModeEvent.TYPE, new ChangeModeHander() {
            @Override
            public void onModeChanged(Mode mode) {
                //todo: reflect mode changing on popup
                String stateName = mode.getName();
                lblState.setText(stateName);
                lblStateDescription.setText(mode.getDescription());
            }
        });
    }

    @UiTemplate("ChromeExt.ui.xml")
    interface ChromeExtPopupUiBinder extends UiBinder<Widget, ChromeExtPopup> {}

    @UiFactory
    DialogBox thatsJustMe() {
        return this;
    }

    @Override
    protected void onPreviewNativeEvent(Event.NativePreviewEvent event) {
        NativeEvent nativeEvent = event.getNativeEvent();
        if (!event.isCanceled() && (event.getTypeInt() == Event.ONCLICK) && isCloseEvent(nativeEvent)) {
            ChromeExt.hideExtension();
        }
        super.onPreviewNativeEvent(event);
    }

    private boolean isCloseEvent(NativeEvent nativeEvent) {
        return nativeEvent.getEventTarget().equals(close.getElement());
    }
}
