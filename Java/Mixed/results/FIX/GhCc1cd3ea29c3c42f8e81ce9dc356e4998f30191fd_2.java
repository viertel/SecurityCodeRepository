class GhCc1cd3ea29c3c42f8e81ce9dc356e4998f30191fd_2{
protected Object get(Map<String, ? extends Object> context, TimeZone timeZone, Locale locale) {
             Object obj = null;
             try {
                 obj = UelUtil.evaluate(context, new String(this.bracketedOriginal));
             } catch (PropertyNotFoundException e) {
                 if (Debug.verboseOn()) {
                     Debug.logVerbose("Error evaluating expression " + this + ": " + e, module);
                 }
            } catch (Exception e) {
                Debug.logError("Error evaluating expression " + this + ": " + e, module);
            }
            return obj;
}
        
}