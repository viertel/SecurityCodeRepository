class GhC9591f52557d52164713556328cd48745c225ed01_1{
public String readLine() throws IOException{
 
         //start with the StringBuffer empty
         lineBuffer.delete(0, lineBuffer.length());
 
         /* This boolean tells which state we are in,
          * depending upon whether or not we got a CR
          * in the preceding read().
         */ 
        boolean cr_just_received = false;

        // Until we add support for specifying a maximum line lenth as
        // a Service Extension, limit lines to 2K, which is twice what
        // RFC 2821 4.5.3.1 requires.
        while (lineBuffer.length() <= 2048) {
            int inChar = read();

            if (!cr_just_received){
                 //the most common case, somewhere before the end of a line
                 switch (inChar){
                     case CR  :  cr_just_received = true;
                                 break;
                     case EOF :  return null;   // premature EOF -- discards data(?)
                     case LF  :  //the normal ending of a line
                         if (tainted == -1) tainted = lineBuffer.length();
                         // intentional fall-through
                     default  :  lineBuffer.append((char)inChar);
                 }
             }else{
                 // CR has been received, we may be at end of line
                 switch (inChar){
                     case LF  :  // LF without a preceding CR
                         if (tainted != -1) {
                             int pos = tainted;
                             tainted = -1;
                             throw new TerminationException("\"bare\" CR or LF in data stream", pos);
                         }
                         return lineBuffer.toString();
                     case EOF :  return null;   // premature EOF -- discards data(?)
                     case CR  :  //we got two (or more) CRs in a row
                         if (tainted == -1) tainted = lineBuffer.length();
                         lineBuffer.append((char)CR);
                         break;
                     default  :  //we got some other character following a CR
                         if (tainted == -1) tainted = lineBuffer.length();
                         lineBuffer.append((char)CR);
                         lineBuffer.append((char)inChar);
                         cr_just_received = false;
                }
            }
        }//while
        throw new LineLengthExceededException("Exceeded maximum line length");
    }
}