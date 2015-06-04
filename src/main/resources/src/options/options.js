/**
 * This is extension's options javascript.
 */

//load options on startup
function initStorageValues() {


    chrome.storage.sync.get("chromeExtOptions", function (items) {
        var saveInitValue = false;
        if (!items.chromeExtOptions) {
            items.chromeExtOptions = {
                preSelectTimeout: 1000,
                apiHost: undefined,
                apiUser: undefined,
                apiPassword: undefined
            };
            saveInitValue = true;
        }

        var co = items.chromeExtOptions;

        var timeout = document.getElementById("preselectTimeout");
        timeout.value = co.preSelectTimeout;

        var apiHost = document.getElementById("apiHost");
        var apiUser = document.getElementById("apiUser");
        var apiPassword = document.getElementById("apiPassword");

        apiHost.value = co.apiHost ? co.apiHost : "";
        apiUser.value = co.apiUser ? co.apiUser : "";
        apiPassword.value = co.apiPassword ? co.apiPassword : "";

        if (saveInitValue) {
            save_options();
        }
        document.preSelectTimeout = co.preSelectTimeout;
    });
}

function save_options() {
    var apiHost = document.getElementById("apiHost");
    var apiUser = document.getElementById("apiUser");
    var apiPassword = document.getElementById("apiPassword");
    var timeout = document.getElementById("preselectTimeout");


    var options = {
        preSelectTimeout: timeout.value,
        apiHost: apiHost.value,
        apiUser: apiUser.value,
        apiPassword: apiPassword.value
    };

    chrome.storage.sync.set({
            chromeExtOptions: options
        },
        function () {
            //do nothing currently
    });
}

function restore_options() {

}

document.addEventListener("DOMContentLoaded", function () {
    initStorageValues();
    document.getElementById("save").addEventListener("click", save_options);
});