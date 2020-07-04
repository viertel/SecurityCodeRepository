class GhCe67f6882118f2a8285e4e8acd050dad64a3ef3e4_4{
/**
     * Set the specified request attribute to the specified value.
     *
     * @param name  Name of the request attribute to set
     * @param value The associated value
     */
    public void setAttribute(String name, Object value) {

        if (System.getSecurityManager() != null &&
                name.equals("org.apache.tomcat.sendfile.filename")) {
            // Use the canonical file name to avoid any possible symlink and
            // relative path issues
            String canonicalPath;
            try {
                canonicalPath = new File(value.toString()).getCanonicalPath();
            } catch (IOException e) {
                SecurityException se = new SecurityException(sm.getString(
                        "coyoteRequest.sendfileNotCanonical", value));
                se.initCause(e);
                throw se;
            }
            // Sendfile is performed in Tomcat's security context so need to
            // check if the web app is permitted to access the file while still
            // in the web app's security context
            System.getSecurityManager().checkRead(canonicalPath);
            // Update the value so the canonical path is used
            value = canonicalPath;
        }

        Object oldValue = null;
        boolean replaced = false;


        oldValue = attributes.put(name, value);
        if (oldValue != null) {
            replaced = true;
        }

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0))
            return;
        ServletRequestAttributeEvent event = null;
        if (replaced)
            event =
                    new ServletRequestAttributeEvent(context.getServletContext(),
                            getRequest(), name, oldValue);
        else
            event =
                    new ServletRequestAttributeEvent(context.getServletContext(),
                            getRequest(), name, value);

        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletRequestAttributeListener))
                continue;
            ServletRequestAttributeListener listener =
                    (ServletRequestAttributeListener) listeners[i];
            try {
                if (replaced) {
                    listener.attributeReplaced(event);
                } else {
                    listener.attributeAdded(event);
                }
            } catch (Throwable t) {
                context.getLogger().error(sm.getString("coyoteRequest.attributeEvent"), t);
                // Error valve will pick this execption up and display it to user
                attributes.put(Globals.EXCEPTION_ATTR, t);
            }
        }
    }
}