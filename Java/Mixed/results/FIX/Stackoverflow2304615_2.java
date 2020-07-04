class Stackoverflow2304615_2{
void vulnerable(){
StringBuilder comment = new StringBuilder();
comment.append("<div class=\"comment\">");
comment.append(StringEscapeUtils.escapeHtml4(input));
comment.append("</div>");
}
}