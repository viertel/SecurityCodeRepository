class Stackoverflow45503716_1{
void vulnerable(){
TransformerFactory factory = TransformerFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
StreamSource xslStream = new StreamSource(inputXSL);
Transformer transformer = factory.newTransformer(xslStream);
}
}