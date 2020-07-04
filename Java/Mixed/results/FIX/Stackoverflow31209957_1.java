class Stackoverflow31209957_1{
void vulnerable(){
String safe = ESAPI.encoder().encodeForHTMLAttribute( request.getParameter( "input" ) );
}
}