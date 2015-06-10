
var inp = document.getElementById("gwtPreSelectTimeout");
document.chromeExtOptions = inp.value;
//remove hidden input for security reasons
document.removeChild(inp);
window.showChromeExt();

