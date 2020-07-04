class Stackoverflow22533425_1{
void vulnerable(){
String safe = ESAPI.encoder().encodeForJavaScript( request.getParameter( "input" ) );
}
}