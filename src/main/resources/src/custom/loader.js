if (window.showChromeExt) {
    var inp = document.getElementById("gwtPreSelectTimeout");
    document.chromeExtOptions = inp.value;
    //remove hidden input for security reasons
    var parent = inp.parentNode;
    parent.removeChild(inp);
    window.showChromeExt();
}

