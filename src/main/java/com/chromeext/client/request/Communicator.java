package com.chromeext.client.request;

import com.chromeext.client.events.APICallEvent;
import com.chromeext.client.model.CallResult;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author Andrew Kharchenko
 */
public class Communicator {

    private static String apiHost;
    private static String apiUser;
    private static String apiPassword;
    private static EventBus eventBus;

    private static final String URI_STATE = "/v3/campaign-explorer/state";
    private static final String URI_PREDICATE = "/v3/campaign-explorer/predicate";


    public static void setupEnvironment(EventBus eventBus, String apiHost, String apiUser, String apiPassword) {
        Communicator.eventBus = eventBus;
        Communicator.apiHost = apiHost;
        Communicator.apiUser = apiUser;
        Communicator.apiPassword = apiPassword;
    }


    public static void getState() {

        checkEnvironment();

        try {
            RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, apiHost + URI_STATE);
            rb.setUser(apiUser);
            rb.setPassword(apiPassword);

            rb.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response)  {
                    int statusCode = response.getStatusCode();
                    if (statusCode != 200) {
                        eventBus.fireEvent(new APICallEvent(new CallResult(true, response.getText())));
                    } else {
                        eventBus.fireEvent(new APICallEvent(new CallResult(response.getText())));
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    eventBus.fireEvent(new APICallEvent(new CallResult(true, exception.getMessage())));
                }
            });
        } catch (RequestException e) {
            eventBus.fireEvent(new APICallEvent(new CallResult(true, e.getMessage())));
        }
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
}
