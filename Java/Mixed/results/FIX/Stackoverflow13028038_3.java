class Stackoverflow13028038_3{
void vulnerable(){
String name = getValueOfName();
SafeHtmlBuilder shb = new SafeHtmlBuilder();
shb.appendEscaped("Name: ").appendEscaped(name);
HTML widget = new HTML(shb.toSafeHtml());
}
}