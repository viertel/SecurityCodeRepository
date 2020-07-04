class Stackoverflow13028038_2{
void vulnerable(){
String name = getValueOfName();
HTML widget = new HTML(SafeHtmlUtils.fromString(name));
}
}