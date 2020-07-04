class Stackoverflow22460708_1{
void vulnerable(){
Jsoup.clean(dirtyHTML, 
                Whitelist.relaxed()
                .addProtocols("img","src","data")
                .addAttributes(":all", "style")
                .addTags("span"));
}
}