class Stackoverflow31045424_1{
void vulnerable(){
String request_origin = request.getHeader("referer");

//check if origin of the request 
//is coming from known source
if(!knownURIs(request_origin)){ 
    //reject the request 
}
else{
    //process request
}
}
}