class Stackoverflow22533425_2{
void vulnerable(){
String safe = ESAPI.encoder().encodeForHTMLAttribute( request.getParameter( "input" ) );
}
}