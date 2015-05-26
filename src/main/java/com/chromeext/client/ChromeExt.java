package com.chromeext.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ChromeExt implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        //for testing purposes...
        exposeGWTMethods();

//        ChromeExtPopup popup = new ChromeExtPopup();
//        popup.show();

    }

    public static void showExtension() {
        ChromeExtPopup popup = new ChromeExtPopup();
        popup.show();
    }

    public static native void exposeGWTMethods()/*-{
        $wnd.showChromeExt = function() {
            @com.chromeext.client.ChromeExt::showExtension()();
        }
    }-*/;
}
