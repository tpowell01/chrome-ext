
chrome.runtime.onMessage.addListener(function(msg, sender, sendResponse) {
    chrome.storage.sync.get("preSelectTimeout", function (items) {
        if (!items.preSelectTimeout) {
            items.preSelectTimeout = 1000;
        }

        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.value = items.preSelectTimeout;
        inp.id = "gwtPreSelectTimeout";
        document.body.appendChild(inp);
    });

    if (msg.text && (msg.text == "load_extension") && !document.gwtExtensionLoaded) {

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

        script.onload = function() {
            //show extension popup once script is loaded
            setTimeout(function() {
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
});

