class Stackoverflow38017721_1{
static {
    _parserFactory = DocumentBuilderFactory.newInstance();

    /* CVE-2016-3720 */
    try {
        _parserFactory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
    } catch (ParserConfigurationException e) {
        e.printStackTrace();
    }
    // Move this line from the static block lower in the file.
    _parserFactory.setNamespaceAware(true);

}
}