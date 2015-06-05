package com.chromeext.client;

import com.chromeext.client.events.ChangeModeEvent;
import com.chromeext.client.events.ChangeModeHander;
import com.chromeext.client.forms.DivForm;
import com.chromeext.client.forms.EmptyForm;
import com.chromeext.client.forms.InputForm;
import com.chromeext.client.forms.UnsupportedElementForm;
import com.chromeext.client.model.Mode;
import com.chromeext.client.model.TargetModel;
import com.chromeext.client.model.TargetType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
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
    @UiField FlowPanel fpFormContainer;

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

        //todo: remove after testing
        //todo: test XS requests

        btnSave.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "http://google.com");
                rb.setCallback(new RequestCallback() {
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        GWT.log("SUCCESS: " + response);
                    }

                    @Override
                    public void onError(Request request, Throwable exception) {
                        GWT.log("ERROR: " + exception.getMessage(), exception);
                    }
                });
                try {
                    Request request = rb.send();
                } catch (RequestException e) {
                    GWT.log(e.getMessage(), e);
                }
            }
        });


    }

    private void initEvents() {
        eventBus.addHandler(ChangeModeEvent.TYPE, new ChangeModeHander() {
            @Override
            public void onModeChanged(Mode mode, TargetModel tm) {
                if (tm == null) {
                    return;
                }
                JavaScriptObject target = tm.getTarget();
                TargetType tt = tm.getType();

                String stateName = mode.getName();
                lblState.setText(stateName);
                String description = mode.getDescription();
                lblStateDescription.setText(description);

                //outline with green
                if (Mode.PRESELECTED.equals(mode) && target != null) {
                    description = description.replace("{0}", getFormattedElementData(target));
                    lblStateDescription.setText(description);
                    removeOutlineFromElement(target, "chromeext-outline-red");
                    outlineElement(target, "chromeext-outline-green");
                    resetFormSection();
                }

                //remove all outlines
                if (Mode.UNSELECTED.equals(mode) && target != null) {
                    removeOutlineFromElement(target, "chromeext-outline-red");
                    removeOutlineFromElement(target, "chromeext-outline-green");
                    resetFormSection();
                }

                //outline with red
                if (Mode.SELECTED.equals(mode) && target != null) {
                    removeOutlineFromElement(target, "chromeext-outline-green");
                    outlineElement(target, "chromeext-outline-red");

                    if (tt != null) {
                        fpFormContainer.clear();
                        switch (tt) {
                            case UNSUPPORTED:
                                fpFormContainer.add(new UnsupportedElementForm());
                                break;
                            case INPUT:
                                fpFormContainer.add(new InputForm());
                                break;
                            case DIV:
                                fpFormContainer.add(new DivForm(getInnerHTML(target)));
                                break;
                        }
                    } else {
                        resetFormSection();
                    }
                }
            }
        });
    }

    private void resetFormSection() {
        fpFormContainer.clear();
        fpFormContainer.add(new EmptyForm());
    }

    @UiTemplate("ChromeExtPopup.ui.xml")
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

    private static native void outlineElement(JavaScriptObject element, String className)/*-{
        element.className += " " + className;
    }-*/;

    private static native void removeOutlineFromElement(JavaScriptObject element, String className)/*-{
        var cn = element.className;
        if (cn) {
            cn = cn.replace(className, '');
            element.className = cn;
        }
    }-*/;

    private static native String getFormattedElementData(JavaScriptObject element)/*-{
        var result = "";
        result += element.tagName;
        if (element.id) {
            result += " #" + element.id;
        } else if (element.className) {
            result += " ." + element.className;
        }
        return result;
    }-*/;

    private static native String getInnerHTML(JavaScriptObject element)/*-{
        var result = "<empty>";
        result = element.innerHTML;
        if (element && element.innerHTML) {
            result = element.innerHTML;
        }
        return result;
    }-*/;
}
