chrome.runtime.onMessage.addListener(function (msg, sender, sendResponse) {

    if (msg.text && msg.text == "load_extension") {
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
    } else if (msg.text && msg.text == "unload_extension") {
        var head = document.getElementsByTagName("head")[0];
        var script2 = document.createElement("script");
        script2.type = "text/javascript";
        script2.src = chrome.runtime.getURL("src/custom/unloader.js");
        head.appendChild(script2);
    }


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
                    script2.src = chrome.runtime.getURL("src/custom/loader.js");
                    head.appendChild(script2);
                }, 500);
            };

            script.src = gwtNocacheURL;
            head.appendChild(script);

            _gwtExt_initMessageElement();

            document.gwtExtensionLoaded = true;
        } else {
            var head = document.getElementsByTagName("head")[0];
            var script2 = document.createElement("script");
            script2.type = "text/javascript";
            script2.src = chrome.runtime.getURL("src/custom/loader.js");
            head.appendChild(script2);
        }
    }
}

function _gwtExt_initMessageElement() {
    var inp = document.createElement("input");
    inp.type = "hidden";
    inp.id = "gwt2ExtMessageBus";


    document.body.appendChild(inp);

    //start listening the value change in message bus
    setInterval(function () {
        var bus = document.getElementById("gwt2ExtMessageBus");
        var msg = bus.value;

        if (msg) {
            //clear value in bus immediately to prevent continuous API call
            bus.value = "";
            var json = JSON.parse(msg);

            if (json.requestType == "STATE") {
                getState();
            } else if (json.requestType == "PREDICATE") {

            } else {
                //respond with unknown requestType to stop showing loader icon in popup
            }
        }

            //todo: implement sending CORS request to API server
    }, 100);
}

function getState() {
    var r = new XMLHttpRequest();
    //todo: replace hardcodes with data from options
    r.open("GET", "http://52.24.201.48:8080/v3/campaign-explorer/state", true, "api_user", "n89asydfnpya97dgfo8asd7g");
    r.onreadystatechange = function() {
        if (r.readyState == 4) {
            if (r.status == 200) {
                var out = document.getElementById("ext2GWTMessageBus");
                //here should b a JSON
                var json = JSON.parse(r.responseText);
                var response = {
                    "requestType" : "STATE",
                    "response" : json
                };

                var stringified = JSON.stringify(response);
                out.value = stringified;
            } else {
                //todo: send error to the GWT
            }
        }
    };
    r.send();
}