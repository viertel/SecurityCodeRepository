class Stackoverflow34147407_1{
void vulnerable(){
if (Boolean.getBoolean("HTTP_ONLY_SESSION")) HttpOnlyConfig.enable(event);
}
public class HttpOnlyConfig
{
    public static void enable(ServletContextEvent event)
    {
        ServletContext servletContext = event.getServletContext();
        Field f;
        try
        { // WARNING TOMCAT6 SPECIFIC!!
            f = servletContext.getClass().getDeclaredField("context");
            f.setAccessible(true);
            org.apache.catalina.core.ApplicationContext ac = (org.apache.catalina.core.ApplicationContext) f.get(servletContext);
            f = ac.getClass().getDeclaredField("context");
            f.setAccessible(true);
            org.apache.catalina.core.StandardContext sc = (StandardContext) f.get(ac);
            sc.setUseHttpOnly(true);
        }
        catch (Exception e)
        {
            System.err.print("HttpOnlyConfig cant enable");
            e.printStackTrace();
        }
    }
}
}