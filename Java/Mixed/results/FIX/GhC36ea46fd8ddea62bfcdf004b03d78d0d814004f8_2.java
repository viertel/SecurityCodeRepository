class GhC36ea46fd8ddea62bfcdf004b03d78d0d814004f8_2{
public static void securityClassLoad(ClassLoader loader)
         throws Exception {
 
         if( System.getSecurityManager() == null ){
             return;
         }

        loadCorePackage(loader);
        loadLoaderPackage(loader);
        loadServletsPackage(loader);
        loadSessionPackage(loader);
        loadUtilPackage(loader);
        loadJavaxPackage(loader);
         loadCoyotePackage(loader);        
         loadHttp11Package(loader);        
         loadTomcatPackage(loader);
     }
private static final void loadServletsPackage(ClassLoader loader)
            throws Exception {
        final String basePackage = "org.apache.catalina.servlets.";
        // Avoid a possible memory leak in the DefaultServlet when running with
        // a security manager. The DefaultServlet needs to load an XML parser
        // when running under a security manager. We want this to be loaded by
        // the container rather than a web application to prevent a memory leak
        // via web application class loader.
        loader.loadClass(basePackage + "DefaultServlet");
    }
}