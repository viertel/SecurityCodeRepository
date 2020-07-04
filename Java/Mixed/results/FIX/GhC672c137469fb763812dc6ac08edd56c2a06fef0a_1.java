class GhC672c137469fb763812dc6ac08edd56c2a06fef0a_1{
public void fillXfaForm(InputSource is) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();     		
    	DocumentBuilder db = dbf.newDocumentBuilder(); 
        db.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(new StringReader(""));
			}        	
        });
    	Document newdoc = db.parse(is);
    	fillXfaForm(newdoc.getDocumentElement());
    }
}