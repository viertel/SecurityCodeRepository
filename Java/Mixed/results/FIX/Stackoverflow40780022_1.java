class Stackoverflow40780022_1{
void vulnerable(){
Whitelist whitelist = Whitelist.none();

    whitelist
        .addTags("a")
        .addAttributes("a", "href")
        .addProtocols("a", "href", "http", "https", "mailto");
}
}