class Stackoverflow16597348_1{
void vulnerable(){
HtmlFormat.escape("<b>hello</b>").toString();
}
}