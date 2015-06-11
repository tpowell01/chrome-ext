package com.chromeext.client.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Form for section C where state (or results) of requests to API presented. This form is for successful response results.
 *
 * @author Andrew Kharchenko
 */
public class ContextStateForm extends BaseForm {

    @UiField Label lblToken;
    @UiField Label lblNumberOfPages;
    @UiField Label lblElementsCount;
    @UiField SimplePanel spActivePredicates;
    @UiField SimplePanel spActions;

    private static ContextStateFormUiBinder uiBinder = GWT.create(ContextStateFormUiBinder.class);

    public ContextStateForm(JSONObject data) {
        uiBinder.createAndBindUi(this);

        parseDataAndFillForm(data);
    }

    private void parseDataAndFillForm(JSONObject data) {
        String token = data.get("token").isString().stringValue();
        lblToken.setText(token);

        JSONNumber nop = data.get("number_of_pages").isNumber();
        String numberOfPages = nop.toString();
        lblNumberOfPages.setText(numberOfPages);

        JSONNumber ec = data.get("element_count").isNumber();
        String elementCount = ec.toString();
        lblElementsCount.setText(elementCount);

        JSONArray jActivePredicates = data.get("active_predicates").isArray();

        if (jActivePredicates.size() > 0) {
            UListElement predicatesList = Document.get().createULElement();

            for (int i = 0; i < jActivePredicates.size(); i++) {
                JSONValue predicate = jActivePredicates.get(i);
                String predicateValue = predicate.isString().stringValue();
                LIElement li = Document.get().createLIElement();
                li.setInnerText(predicateValue);
                predicatesList.appendChild(li);
            }

            spActivePredicates.getElement().appendChild(predicatesList);
        }

        JSONArray actionsArray = data.get("actions").isArray();
        if (actionsArray.size() > 0) {

            JSONObject actions = actionsArray.get(0).isObject();

            UListElement actionsList = Document.get().createULElement();
            LIElement li;

            String actionToken = actions.get("action_token").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("action_token: " + actionToken);
            actionsList.appendChild(li);

            String actionOrder = actions.get("action_order").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("action_order: " + actionOrder);
            actionsList.appendChild(li);

            String action = actions.get("action").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("action: " + action);
            actionsList.appendChild(li);

            String url = actions.get("url").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("url: " + url);
            actionsList.appendChild(li);

            String loadedFromCache = actions.get("loaded_from_cache").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("loaded_from_cache: " + loadedFromCache);
            actionsList.appendChild(li);

            String pageSize = actions.get("page_size").isString().stringValue();
            li = Document.get().createLIElement();
            li.setInnerText("page_size: " + pageSize);
            actionsList.appendChild(li);

            spActions.getElement().appendChild(actionsList);
        }
    }

    @UiTemplate("ContextStateForm.ui.xml")
    interface ContextStateFormUiBinder extends UiBinder<Widget, ContextStateForm> {
    }

    @UiFactory
    public BaseForm me() {
        return this;
    }
}
