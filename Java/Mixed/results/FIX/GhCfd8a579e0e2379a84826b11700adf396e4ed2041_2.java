class GhCfd8a579e0e2379a84826b11700adf396e4ed2041_2{
@Override
     public void log(org.apache.coyote.Request req,
             org.apache.coyote.Response res, long time) {
 
         Request request = (Request) req.getNote(ADAPTER_NOTES);
         Response response = (Response) res.getNote(ADAPTER_NOTES);
        try {
            connector.getService().getContainer().logAccess(
                    request, response, time, true);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.warn(sm.getString("coyoteAdapter.accesslogFail"), t);
        }
     }
}