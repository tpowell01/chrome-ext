
//send message to injected script to load an extension from GWT
chrome.browserAction.onClicked.addListener(function(tab) {
    chrome.tabs.sendMessage(tab.id, {text: "load_extension"});
});

//show GWT popup if tab changed (in case if popup was already opened on some other tab
chrome.tabs.onActivated.addListener(function(tabInfo) {

    var tabId = tabInfo.tabId;
    var windowId = tabInfo.windowId;

    chrome.tabs.query({active: true}, function(tab) {
        //todo: put logic of GWT popup opening in case of tab change
    });
});

//show GWT popup if tab's content changed (navigation or form submission performed)
chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {

});

