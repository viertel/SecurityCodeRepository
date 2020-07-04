class GhCe67f6882118f2a8285e4e8acd050dad64a3ef3e4_2{
void vulnerable(){
if (sendfileData != null) {
                sendfileData.socket = socket;
                sendfileData.keepAlive = keepAlive;
                if (!endpoint.getSendfile().add(sendfileData)) {
                    if (sendfileData.socket == 0) {
                        // Didn't send all the data but the socket is no longer
                        // set. Something went wrong. Close the connection.
                        // Too late to set status code.
                        if (log.isDebugEnabled()) {
                            log.debug(sm.getString(
                                    "http11processor.sendfile.error"));
                        }
                        openSocket = false;
                    } else {
                        openSocket = true;
                    }
                    break;
                }
            }
}
}