package com.chromeext.client;

import com.chromeext.client.events.ChangeModeEvent;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ChromeExt implements EntryPoint {

    //Chrome extension popup
    private static ChromeExtPopup popup;

    //timer which performs element pre-selection when executed
    private Timer captureTimer;

    //current mode (or status) of the extension
    private Mode currentMode = Mode.UNSELECTED;

    //global event preview handler registration
    private static HandlerRegistration handlerRegistration;

    //global event preview handler to track various DOM events on various elements
    private static Event.NativePreviewHandler nativeEventPreviewHandler;

    private JavaScriptObject currentTarget;

    private EventBus eventBus;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        eventBus = new SimpleEventBus();
        popup = new ChromeExtPopup(eventBus);
        eventBus.fireEvent(new ChangeModeEvent(Mode.UNSELECTED));


        exposeGWTMethods();

        captureTimer = new Timer() {
            @Override
            public void run() {
                //todo: change mode, make underlying element (target) bordered with red (better add custom style)
                //1. set mode to pre-select on element
                //2. add red border around pre-selected target element
                currentMode = Mode.PRESELECTED;
                eventBus.fireEvent(new ChangeModeEvent(currentMode));
                log(currentTarget);
            }
        };

        nativeEventPreviewHandler = new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                int eventType = event.getTypeInt();
                EventTarget target = event.getNativeEvent().getEventTarget();
                if (eventType == Event.ONMOUSEMOVE) {
                    //todo: if mode was pre-select - remove border from target
                    currentMode = Mode.UNSELECTED;
                    eventBus.fireEvent(new ChangeModeEvent(currentMode));
                    captureTimer.cancel();
                    captureTimer.schedule(1000); //todo: this value should be configurable from withing Chrome Extension
                } else if (eventType == Event.ONMOUSEOVER) {
                    //catch currently hovered element on page
                    //todo: add logic to skip extension's popup from being captured
                    if (currentMode.equals(Mode.UNSELECTED)) {
                        currentTarget = event.getNativeEvent().getEventTarget();
                    }
                }
            }
        };


        //for testing purposes...
        showExtension(); //todo remove after testing
    }

    public static native void log(JavaScriptObject obj)/*-{
        console.log(obj.tagName);
    }-*/;


    /**
     * This method exposed to javascript to open extension's popup on target page
     */
    public static void showExtension() {
        popup.show();
        handlerRegistration = Event.addNativePreviewHandler(nativeEventPreviewHandler);
    }

    /**
     * This method exposed to javascript to close extension's popup by pressing extension's "browser action" button
     */
    public static void hideExtension() {
        popup.hide();
        handlerRegistration.removeHandler();
    }


    /**
     * Exposing needed method to external javascript to manipulate by extension
     */
    public static native void exposeGWTMethods()/*-{
        $wnd.showChromeExt = function() {
            @com.chromeext.client.ChromeExt::showExtension()();
        };
        $wnd.hideChromeExt = function() {
            @com.chromeext.client.ChromeExt::hideExtension()();
        };
    }-*/;
}
