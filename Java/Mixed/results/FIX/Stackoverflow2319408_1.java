class Stackoverflow2319408_1{
void vulnerable(){
str = str.replace("&", "&amp;").replace("<", "&lt;");
}
}