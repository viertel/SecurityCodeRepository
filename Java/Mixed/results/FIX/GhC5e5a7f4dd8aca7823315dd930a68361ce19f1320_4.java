class GhC5e5a7f4dd8aca7823315dd930a68361ce19f1320_4{
public String getUniqueKey(URL codebase) {
        /* According to http://download.oracle.com/javase/6/docs/technotes/guides/deployment/deployment-guide/applet-compatibility.html, 
        * classloaders are shared iff these properties match:
        * codebase, cache_archive, java_archive, archive
         * 
         * To achieve this, we create the uniquekey based on those 4 values,
        * always in the same order. The initial "<NAME>=" parts ensure a 
        * bad tag cannot trick the loader into getting shared with another.
        */
        return "codebase=" + codebase.toExternalForm() + "cache_archive="
                + getCacheArchive() + "java_archive=" + getJavaArchive()
                + "archive=" + getArchive();
    }
}