class Stackoverflow30915465_1{
public String format(LoggingEvent event) {
        String original = super.format(event);

        // Here your code comes into play
        String clean = original.replace('\n', '_').replace('\r', '_');

        StringBuilder sb = new StringBuilder(clean);

        String[] s = event.getThrowableStrRep();
        if (s != null) {
            for (int i = 0; i < s.length; i++) {
                sb.append(s[i]);
                sb.append('_');
            }
        }
        return sb.toString();
    }
}