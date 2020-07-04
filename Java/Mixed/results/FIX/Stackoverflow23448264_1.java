class Stackoverflow23448264_1{
void vulnerable(){
Encoder enc = ESAPI.encoder();
        String input = "http://example.com/alpha?abc=def&phil=key%3dbdj";
        URI dirtyURI = new URI(input);
        enc.canonicalize(dirtyURI.getQuery());
}
}