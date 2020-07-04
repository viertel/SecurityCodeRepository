class Stackoverflow31312335_1{
void vulnerable(){
DocumentBuilderFactory dbf = null;
        DocumentBuilder db = null;
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        db = dbf.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(datosXml.getBytes());
        Document doc = null;
        doc = db.parse(stream, "");
}
}