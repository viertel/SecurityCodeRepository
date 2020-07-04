class GhC299b26af66793438c323ea6b18462fa44683080f_2{
@Override
     public void servletSecurityAnnotationScan() throws ServletException {
        if (getServlet() == null) {
            Class<?> clazz = null;
            try {
                clazz = getParent().getLoader().getClassLoader().loadClass(
                        getServletClass());
                processServletSecurityAnnotation(clazz);
            } catch (ClassNotFoundException e) {
                // Safe to ignore. No class means no annotations to process
             }
         } else {
             if (servletSecurityAnnotationScanRequired) {
                 processServletSecurityAnnotation(getServlet().getClass());
             }
         }
     }
}