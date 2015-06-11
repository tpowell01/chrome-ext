package com.chromeext.client.request;

import com.chromeext.client.events.APICallEndEvent;
import com.chromeext.client.events.APICallEvent;
import com.chromeext.client.events.APICallStartEvent;
import com.chromeext.client.model.CallResult;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Andrew Kharchenko
 */
public class Communicator {

    private static String apiHost;
    private static String apiUser;
    private static String apiPassword;
    private static EventBus eventBus;

    private static final String URI_PING = "/v3/ping";
    private static final String URI_STATE = "/v3/campaign-explorer/state";
    private static final String URI_PREDICATE = "/v3/campaign-explorer/predicate";


    public static void setupEnvironment(EventBus eventBus, String apiHost, String apiUser, String apiPassword) {
        Communicator.eventBus = eventBus;
        //cut off trailing slash if it is at the end of the apiHost value
        if (apiHost.endsWith("/")) {
            Communicator.apiHost = apiHost.substring(0, apiHost.length() - 2);
        } else {
            Communicator.apiHost = apiHost;
        }

        Communicator.apiUser = apiUser;
        Communicator.apiPassword = apiPassword;
    }


    /**
     * Utility method to make PING to the API server to check whether it working or not.
     */
    public static void pingAPIServer() {
        checkEnvironment();
        fireAPICallStartEvent();

        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, apiHost + URI_PING);
//        rb.setUser(apiUser);
//        rb.setPassword(apiPassword);
        rb.setTimeoutMillis(10000);

        try {
            rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() != 200) {
                        fireErrorEvent("Seems API server is not available");
                    }
                    fireAPICallEndEvent();
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    fireErrorEvent(exception.getMessage());
                    fireAPICallEndEvent();
                }
            });
        } catch (RequestException e) {
            fireErrorEvent(e.getMessage());
            fireAPICallEndEvent();
        }
    }

    public static void getState() {
        checkEnvironment();
        fireAPICallStartEvent();

        InputElement outMessageBus = (InputElement) Document.get().getElementById("gwt2ExtMessageBus");
        outMessageBus.setValue("{\"requestType\": \"STATE\"}");
    }

    public static void parseGetStateResponse(JSONObject response) {
        fireAPICallEndEvent();
        JSONObject stateResponse = response.get("response").isObject();
        eventBus.fireEvent(new APICallEvent(new CallResult(stateResponse)));
    }

    public static void postPredicate(Object... some) {
        checkEnvironment();
        fireAPICallStartEvent();

        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, apiHost + URI_PREDICATE);
        rb.setUser(apiUser);
        rb.setPassword(apiPassword);
        //todo: finish implementation
        fireAPICallEndEvent();
    }

    /**
     * Should be invoked in any request specific method in order to have all pre-requisites set for performing requests
     * to API.
     */
    private static void checkEnvironment() {
        if (eventBus == null || apiHost == null || apiHost.isEmpty() || apiUser == null || apiUser.isEmpty()
                || apiPassword == null || apiPassword.isEmpty()) {
            throw new IllegalStateException("Not all required attributes set to successfully invoke API methods.");
        }
    }

    private static void fireErrorEvent(String errorMessage) {
        eventBus.fireEvent(new APICallEvent(new CallResult(true, errorMessage)));
    }

    private static void fireAPICallStartEvent() {
        eventBus.fireEvent(new APICallStartEvent());
    }

    private static void fireAPICallEndEvent() {
        eventBus.fireEvent(new APICallEndEvent());
    }

    private static native String btoa(String string)/*-{
        return btoa(string);
    }-*/;

}
