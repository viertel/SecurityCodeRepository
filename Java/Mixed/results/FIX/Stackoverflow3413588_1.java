class Stackoverflow3413588_1{
void vulnerable(){
PolicyFactory policy = new HtmlPolicyBuilder()
    .allowElements("a")
    .allowUrlProtocols("https")
    .allowAttributes("href").onElements("a")
    .requireRelNofollowOnLinks()
    .build();

String safeHTML = policy.sanitize(untrustedHTML);
}
}