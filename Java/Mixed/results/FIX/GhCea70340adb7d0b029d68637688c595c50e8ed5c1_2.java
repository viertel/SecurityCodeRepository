class GhCea70340adb7d0b029d68637688c595c50e8ed5c1_2{
private SmsMessage updateMessageStatus(Context context, Uri messageUri, byte[] pdu,
             String format) {
         SmsMessage message = SmsMessage.createFromPdu(pdu, format);
         if (message == null) {
             return null;
         }
         // Create a "status/#" URL and use it to update the
         // message's status in the database.
         Cursor cursor = SqliteWrapper.query(context, context.getContentResolver(),
                             messageUri, ID_PROJECTION, null, null, null);
 
         try {
             if (cursor.moveToFirst()) {
                 int messageId = cursor.getInt(0);
 
                 Uri updateUri = ContentUris.withAppendedId(STATUS_URI, messageId);
                 int status = message.getStatus();
                 boolean isStatusReport = message.isStatusReportMessage();
                 ContentValues contentValues = new ContentValues(2);
 
                 if (Log.isLoggable(LogTag.TAG, Log.DEBUG)) {
                     log("updateMessageStatus: msgUrl=" + messageUri + ", status=" + status +
                             ", isStatusReport=" + isStatusReport);
                 }
 
                 contentValues.put(Sms.STATUS, status);
                 contentValues.put(Inbox.DATE_SENT, System.currentTimeMillis());
                 SqliteWrapper.update(context, context.getContentResolver(),
                                     updateUri, contentValues, null, null);
            } else {
                error("Can't find message for status update: " + messageUri);
            }
        } catch(NullPointerException e){
            return null;
        } finally {
            cursor.close();
        }
         return message;
     }
}