class Stackoverflow3846383_1{
void vulnerable(){
String safeHtml = Jsoup.clean(unsafeHtml, Whitelist.basicWithImages());
}
}