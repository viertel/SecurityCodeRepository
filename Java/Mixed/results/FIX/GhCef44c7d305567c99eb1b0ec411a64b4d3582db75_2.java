class GhCef44c7d305567c99eb1b0ec411a64b4d3582db75_2{
public void setDetails(Map details) {

        if (details == null) {
            return;
        }

        final Map detailsCopy = new HashMap(details);

        // Fix for CVE ID 2015-3251
        // Remove sensitive host credential information from
        // the details to prevent leakage through API calls
        detailsCopy.remove("username");
        detailsCopy.remove("password");

        this.details = detailsCopy;

    }
}