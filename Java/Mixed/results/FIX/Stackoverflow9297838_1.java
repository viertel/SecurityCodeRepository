class Stackoverflow9297838_1{
void vulnerable(){
out.print("<p>" + StringEscapeUtils.escapeHtml(request.getParameter("foo")) + "</p>");
}
}