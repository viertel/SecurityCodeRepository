class Stackoverflow3410541_1{
void vulnerable(){
String unsafe = 
      "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
String safe = Jsoup.clean(unsafe, Whitelist.basic());
}
}