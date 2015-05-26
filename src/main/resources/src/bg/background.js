
//send message to injected script to load an extension from GWT
chrome.browserAction.onClicked.addListener(function(tab) {
    chrome.tabs.sendMessage(tab.id, {text: "load_extension"});
});

