class Stackoverflow6598730_1{
void vulnerable(){
foot += "<input type='hidden' name='firstName' value='"+StringEscapeUtils.escapeHtml(firstName)+"'>";
}
}