
//send message to injected script to load an extension from GWT
chrome.browserAction.onClicked.addListener(function(tab) {

    chrome.storage.sync.get("chromeExtLoaded", function(items) {
        if (!items.chromeExtLoaded) {
            chrome.storage.sync.set({chromeExtLoaded: true});
            chrome.tabs.sendMessage(tab.id, {text: "load_extension"});
        } else {
            chrome.storage.sync.set({chromeExtLoaded: false});
            chrome.tabs.sendMessage(tab.id, {text: "unload_extension"});
        }
    });
});

//show GWT popup if tab changed (in case if popup was already opened on some other tab
chrome.tabs.onActivated.addListener(function(tabInfo) {

    var tabId = tabInfo.tabId;
    var windowId = tabInfo.windowId;

    chrome.tabs.query({active: true}, function(tab) {
        chrome.storage.sync.get("chromeExtLoaded", function(items) {
            if (items.chromeExtLoaded) {
                chrome.tabs.sendMessage(tabId, {text: "load_extension"})
            } else {

            }
        });
    });
});

//show GWT popup if tab's content changed (navigation or form submission performed)
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
    chrome.storage.sync.get("chromeExtLoaded", function(items) {
        if (items.chromeExtLoaded) {
            chrome.tabs.sendMessage(tabId, {text: "load_extension"})
        } else {
            chrome.tabs.sendMessage(tab.id, {text: "unload_extension"});
        }
    });
});

