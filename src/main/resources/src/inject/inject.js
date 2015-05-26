
chrome.runtime.onMessage.addListener(function(msg, sender, sendResponse) {
    if (msg.text && (msg.text == "load_extension")) {

        var head = document.getElementsByTagName("head")[0];

        var gwtCSSURL = chrome.runtime.getURL("chromeext/gwt/chrome/chrome.css");
        var link = document.createElement("link");
        link.setAttribute("rel", "stylesheet");
        link.setAttribute("type", "text/css");
        link.setAttribute("href", gwtCSSURL);
        head.appendChild(link);

        var gwtNocacheURL = chrome.runtime.getURL("chromeext/chromeext.nocache.js");
        var script = document.createElement("script");
        script.type = "text/javascript";

        script.onload = function() {
            //show extension popup once script is loaded
            setTimeout(function() {
                console.log(document.getElementsByTagName("title")[0].innerText);
                var script2 = document.createElement("script");
                script2.type = "text/javascript";
                script2.src = chrome.runtime.getURL("src/custom/runner.js");
                head.appendChild(script2);
            }, 1000);
        };

        script.src = gwtNocacheURL;
        head.appendChild(script);


    }
});

/*
chrome.extension.sendMessage({}, function(response) {
	var readyStateCheckInterval = setInterval(function() {
	if (document.readyState === "complete") {
		clearInterval(readyStateCheckInterval);

		// ----------------------------------------------------------
		// This part of the script triggers when page is done loading
		console.log("Hello. This message was sent from scripts/inject.js");
		// ----------------------------------------------------------

	}
	}, 10);
});*/
