class Stackoverflow26964867_1{
void vulnerable(){
Function<HtmlStreamEventReceiver, HtmlSanitizer.Policy> policy
     = new HtmlPolicyBuilder()
         .allowElements("a", "p")
         .allowAttributes("href").onElements("a")
         .toFactory();

 // Sanitize your output.
 HtmlSanitizer.sanitize(myHtml, policy.apply(myHtmlStreamRenderer));
}
}