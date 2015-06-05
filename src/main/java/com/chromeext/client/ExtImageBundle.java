package com.chromeext.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;

/**
 * @author Andrew Kharchenko
 */
public interface ExtImageBundle extends ClientBundle {

    @Source("com/chromeext/client/icons/ajax-loader.gif")
    ImageResourcePrototype getLoadingIcon();
}
