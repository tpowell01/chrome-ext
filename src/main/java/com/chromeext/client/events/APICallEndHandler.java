package com.chromeext.client.events;

/**
 * Handler for {@link com.chromeext.client.events.APICallEndEvent}
 *
 * @author Andrew Kharchenko
 */
public interface APICallEndHandler {
    /**
     * Fired when API call ended
     */
    void onAPICallEnd();
}
