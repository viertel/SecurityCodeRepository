class GhC901292cf9d7d8225f8a3b96c7583e2bd8b41772d_2{
/**
      * Return JAXP document builder instance.
      */
     protected DocumentBuilder getDocumentBuilder()
         throws ServletException {
         DocumentBuilder documentBuilder = null;
         DocumentBuilderFactory documentBuilderFactory = null;
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilderFactory.setExpandEntityReferences(false);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch(ParserConfigurationException e) {
            throw new ServletException
                 (sm.getString("webdavservlet.jaxpfailed"));
         }
         return documentBuilder;
     }
}