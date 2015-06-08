package com.chromeext.client;

import com.chromeext.client.events.ChangeModeEvent;
import com.chromeext.client.model.Mode;
import com.chromeext.client.model.TargetModel;
import com.chromeext.client.model.TargetType;
import com.chromeext.client.request.Communicator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
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
    private static Mode currentMode = Mode.UNSELECTED;

    //global event preview handler registration
    private static HandlerRegistration handlerRegistration;

    //global event preview handler to track various DOM events on various elements
    private static Event.NativePreviewHandler nativeEventPreviewHandler;

    private static JavaScriptObject currentTarget;

    private static EventBus eventBus;

    private static int preSelectTimeout = 1000;

    private static String apiHost;
    private static String apiUser;
    private static String apiPassword;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        //setup event bus which used for custom events sending and interception between page and extension popup
        eventBus = new SimpleEventBus();
        //initialize extension popup
        popup = new ChromeExtPopup(eventBus);
        //fire first event to popup to set to UNSELECTED state
        eventBus.fireEvent(new ChangeModeEvent(Mode.UNSELECTED, null));

        //expose methods to be used from outside of GWT code (by extension's custom JSs)
        exposeGWTMethods();

        //setup a timer which will set to pre-selected mode once mouse stopped over some element after specific amount
        // of time
        captureTimer = new Timer() {
            @Override
            public void run() {
                //1. set mode to pre-select on element
                //2. add red border around pre-selected target element
                if (currentTarget != null) {
                    currentMode = Mode.PRESELECTED;
                    TargetType tt = TargetType.getByType(getTargetType(currentTarget));
                    eventBus.fireEvent(new ChangeModeEvent(currentMode, new TargetModel(currentTarget, tt)));
                    sunkOnContextMenuEvent();
                }
            }
        };

        nativeEventPreviewHandler = new Event.NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
                int eventType = event.getTypeInt();

                //set extension in unselected state if mouse is moving and reset timer for capturing the element
                if (eventType == Event.ONMOUSEMOVE) {
                    if (Mode.PRESELECTED.equals(currentMode) || Mode.UNSELECTED.equals(currentMode)) {
                        currentMode = Mode.UNSELECTED;
                        eventBus.fireEvent(new ChangeModeEvent(currentMode, new TargetModel(currentTarget,
                                TargetType.getByType(getTargetType(currentTarget)))));
                        captureTimer.cancel();
                        captureTimer.schedule(preSelectTimeout);
                        restoreOnContextMenuEvent();
                    }
                } else if (eventType == Event.ONMOUSEOVER) { //capture element on mouse over. capture only submittable element
                    //catch currently hovered element on page
                    if (currentMode.equals(Mode.UNSELECTED)) {
                        EventTarget target = event.getNativeEvent().getEventTarget();
                        Element elm = Element.as(target);
                        //capture only elements that are not belong to popup itself
                        if (popup.getElement().isOrHasChild(elm)) {
                            currentTarget = null;
                        } else {
                            currentTarget = target;
                        }
                    }
                }
            }
        };

        //for testing purposes...
        showExtension(); //todo remove after testing
    }

    /**
     * This method exposed to javascript to open extension's popup on target page
     */
    public static void showExtension() {
        //invoke extension configuration based on extension options set
        setupConfiguration();

        //setup communicator for further API calls invocations and processing
        Communicator.setupEnvironment(eventBus, apiHost, apiUser, apiPassword);

        popup.show();

        handlerRegistration = Event.addNativePreviewHandler(nativeEventPreviewHandler);
    }

    private static void handleRightClickInPreSelectedMode() {
        if (Mode.SELECTED.equals(currentMode)) {
            currentMode = Mode.PRESELECTED;
            eventBus.fireEvent(new ChangeModeEvent(currentMode, new TargetModel(currentTarget,
                    TargetType.getByType(getTargetType(currentTarget)))));
        } else {
            currentMode = Mode.SELECTED;
            eventBus.fireEvent(new ChangeModeEvent(currentMode, new TargetModel(currentTarget,
                    TargetType.getByType(getTargetType(currentTarget)))));
        }
    }

    /**
     * This method exposed to javascript to close extension's popup by pressing extension's "browser action" button
     */
    public static void hideExtension() {
        currentMode = Mode.UNSELECTED;
        eventBus.fireEvent(new ChangeModeEvent(currentMode, new TargetModel(currentTarget,
                TargetType.getByType(getTargetType(currentTarget)))));
        popup.hide();
        handlerRegistration.removeHandler();
        restoreOnContextMenuEvent();
    }


    /**
     * Exposing needed method to external javascript to manipulate by extension
     */
    public static native void exposeGWTMethods()/*-{
        $wnd.showChromeExt = function () {
            @com.chromeext.client.ChromeExt::showExtension()();
        };
        $wnd.hideChromeExt = function () {
            @com.chromeext.client.ChromeExt::hideExtension()();
        };
    }-*/;

    //checks if hovered html element is form submittable element or not
    public static native int getTargetType(JavaScriptObject obj)/*-{
        var result = 0;
        if (obj) {
            var tagName = obj.tagName;
            if (tagName) {
                var lcTagName = tagName.toLowerCase();
                result = (lcTagName == "input" || lcTagName == "textarea"
                    || lcTagName == "button" || lcTagName == "select") ? 1 :
                    ((lcTagName == "div" || lcTagName == "span" || lcTagName == "p") ? 2 : 0);

            }
        }
        return result;
    }-*/;


    public static native void sunkOnContextMenuEvent()/*-{
        $doc.oldOnContextMenu = $doc.oncontextmenu;
        $doc.oncontextmenu = function (evt) {
            @com.chromeext.client.ChromeExt::handleRightClickInPreSelectedMode()();
            return false;
        };
    }-*/;

    public static native void restoreOnContextMenuEvent()/*-{
        $doc.oncontextmenu = $doc.oldOnContextMenu;
    }-*/;

    public static native String getChromeExtConfiguration()/*-{
        return $doc.chromeExtOptions;
    }-*/;


    /**
     * Setup initial extension's configuration
     */
    private static void setupConfiguration() {
        String cfg = getChromeExtConfiguration();
        JSONObject config = JSONParser.parseLenient(cfg).isObject();
        JSONNumber pst = config.get("preSelectTimeout").isNumber();
        int timeout = Double.valueOf(pst.doubleValue()).intValue();
        if (timeout > 0) {
            preSelectTimeout = timeout;
        }
        JSONString jsonApiHost = config.get("apiHost").isString();
        apiHost = jsonApiHost.stringValue();
        JSONString jsonApiUser = config.get("apiUser").isString();
        apiUser = jsonApiUser.stringValue();
        JSONString jsonApiPassword = config.get("apiPassword").isString();
        apiPassword = jsonApiPassword.stringValue();
    }
}
