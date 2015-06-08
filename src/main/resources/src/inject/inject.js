chrome.runtime.onMessage.addListener(function (msg, sender, sendResponse) {
    chrome.storage.sync.get("chromeExtOptions", function (items) {
        if (!items.chromeExtOptions) {
            //show alert that not all options are set for the plugin
            alert("Please configure extension on Options page before using it.");
        } else {
            var ceo = items.chromeExtOptions;
            if (!ceo.preSelectTimeout) {
                ceo.preSelectTimeout = 1000;
            }

            if (!ceo.apiHost || !ceo.apiUser || !ceo.apiPassword) {
                alert("Not all configuration options for extension are set.");
            } else {
                var inp = document.createElement("input");
                inp.type = "hidden";

                inp.value = JSON.stringify(ceo);

                inp.id = "gwtPreSelectTimeout";
                document.body.appendChild(inp);

                _gwtExt_loadExtension(msg);
            }
        }
    });

});

function _gwtExt_loadExtension(msg) {
    if (msg.text && (msg.text == "load_extension")) {
        if (!document.gwtExtensionLoaded) {

            var head = document.getElementsByTagName("head")[0];

            var gwtCSSURL = chrome.runtime.getURL("chromeext/gwt/chrome/chrome.css");
            var link = document.createElement("link");
            link.setAttribute("rel", "stylesheet");
            link.setAttribute("type", "text/css");
            link.setAttribute("href", gwtCSSURL);
            head.appendChild(link);

            link = document.createElement("link");
            link.setAttribute("rel", "stylesheet");
            link.setAttribute("type", "text/css");
            link.setAttribute("href", chrome.runtime.getURL("chromeext/ChromeExt.css"));
            head.appendChild(link);

            var gwtNocacheURL = chrome.runtime.getURL("chromeext/chromeext.nocache.js");
            var script = document.createElement("script");
            script.type = "text/javascript";

            script.onload = function () {
                //show extension popup once script is loaded
                setTimeout(function () {
                    var script2 = document.createElement("script");
                    script2.type = "text/javascript";
                    script2.src = chrome.runtime.getURL("src/custom/runner.js");
                    head.appendChild(script2);
                }, 1000);
            };

            script.src = gwtNocacheURL;
            head.appendChild(script);
            document.gwtExtensionLoaded = true;
        } else {
            var head = document.getElementsByTagName("head")[0];
            var script2 = document.createElement("script");
            script2.type = "text/javascript";
            script2.src = chrome.runtime.getURL("src/custom/runner.js");
            head.appendChild(script2);
        }
    }
}

