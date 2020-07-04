class Stackoverflow44664649_1{
public ContainerRequest filter( ContainerRequest request )
    {
        // Clean the query strings
        cleanParams( request.getQueryParameters() );

        // Clean the headers
        cleanParams( request.getRequestHeaders() );

        // Clean the cookies
        cleanParams( request.getCookieNameValueMap() );

        // Return the cleansed request
        return request;
    }

    /**
     * Apply the XSS filter to the parameters
     * @param parameters
     */
    private void cleanParams( MultivaluedMap<String, String> parameters )
    {
        LOG.debug( "Checking for XSS Vulnerabilities: {}", parameters );

        for( Map.Entry<String, List<String>> params : parameters.entrySet() )
        {
            String key = params.getKey();
            List<String> values = params.getValue();

            List<String> cleanValues = new ArrayList<String>();
            for( String value : values )
            {
                cleanValues.add( stripXSS( value ) );
            }

            parameters.put( key, cleanValues );
        }

        LOG.debug( "XSS Vulnerabilities removed: {}", parameters );
    }

    /**
     * Strips any potential XSS threats out of the value
     * @param value
     * @return
     */
    public String stripXSS( String value )
    {
        if( value == null )
            return null;

        // Use the ESAPI library to avoid encoded attacks.
        value = ESAPI.encoder().canonicalize( value );

        // Avoid null characters
        value = value.replaceAll("\0", "");

        // Clean out HTML
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        outputSettings.escapeMode( EscapeMode.xhtml );
        outputSettings.prettyPrint( false );
        value = Jsoup.clean( value, "", Whitelist.none(), outputSettings );

        return value;
    }
}