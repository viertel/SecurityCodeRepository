class GhCa2538ce78f83b7376c48d12d8247600079d789b1_2{
/**
      * Finish AJP response.
      */
     protected void finish() throws IOException {
 
         if (!response.isCommitted()) {
             // Validate and write response headers
             try {
                 prepareResponse();
             } catch (IOException e) {
                 // Set error flag
                 error = true;
             }
         }
 
         if (finished)
             return;

        finished = true;

        // Swallow the unread body packet if present
        if (first && request.getContentLengthLong() > 0) {
            receive();
        }

        // Add the end message
        if (error) {
            output(endAndCloseMessageArray, 0, endAndCloseMessageArray.length);
         } else {
             output(endMessageArray, 0, endMessageArray.length);
         }
     }
}