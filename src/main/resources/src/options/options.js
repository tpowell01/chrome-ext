//load options on startup
function initStorageValues() {
    chrome.storage.sync.get("preSelectTimeout", function (items) {
        var saveInitValue = false;
        if (!items.preSelectTimeout) {
            items.preSelectTimeout = 1000;
            saveInitValue = true;
        }
        var currtimeout = document.getElementById("currentPreselectTimeout");
        currtimeout.innerText = items.preSelectTimeout;
        var timeout = document.getElementById("preselectTimeout");
        timeout.value = items.preSelectTimeout;
        if (saveInitValue) {
            save_options();
        }
        document.preSelectTimeout = items.preSelectTimeout;
    });
}

function save_options() {
    var timeout = document.getElementById("preselectTimeout");
    chrome.storage.sync.set({
        preSelectTimeout: timeout.value
    }, function () {
        var currtimeout = document.getElementById("currentPreselectTimeout");
        currtimeout.innerText = timeout.value;
    });
}

function restore_options() {

}

document.addEventListener("DOMContentLoaded", function () {
    initStorageValues();
    document.querySelector("button").addEventListener("click", save_options);
});