class Stackoverflow44958832_1{
private void log(Level level, EventType type, String message, Throwable throwable) {

    // Check to see if we need to log.
    if (!isEnabledFor(level)) {
        return;
    }

    // ensure there's something to log
    if (message == null) {
        message = "";
    }

    // ensure no CRLF injection into logs for forging records
    String clean = message.replace('\n', '_').replace('\r', '_');
    if (ESAPI.securityConfiguration().getLogEncodingRequired()) {
        clean = ESAPI.encoder().encodeForHTML(message);
        if (!message.equals(clean)) {
            clean += " (Encoded)";
        }
    }

    // log server, port, app name, module name -- server:80/app/module
    StringBuilder appInfo = new StringBuilder();
    if (ESAPI.currentRequest() != null && logServerIP) {
        appInfo.append(ESAPI.currentRequest().getLocalAddr()).append(":").append(ESAPI.currentRequest().getLocalPort());
    }
    if (logAppName) {
        appInfo.append("/").append(applicationName);
    }
    appInfo.append("/").append(getName());

    //get the type text if it exists
    String typeInfo = "";
    if (type != null) {
        typeInfo += type + " ";
    }

    // log the message
    // Fix for https://code.google.com/p/owasp-esapi-java/issues/detail?id=268
    // need to pass callerFQCN so the log is not generated as if it were always generated from this wrapper class
    log(Log4JLogger.class.getName(), level, "[" + typeInfo + getUserInfo() + " -> " + appInfo + "] " + clean, throwable);
}
}