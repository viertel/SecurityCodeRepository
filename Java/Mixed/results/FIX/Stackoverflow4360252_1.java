class Stackoverflow4360252_1{
void doGet(HttpServletRequest req, HttpServletResponse resp) {
  String message = req.getParameter("message");
  // Unless I check, in code, that an input is of some other content type,
  // I need to conservatively assume it's plain text.

  resp.setContentType("text/html;charset=UTF-8");

  // Since resp is a channel with content-type text/html,
  // I need to only write HTML to it.
  resp.getWriter().write(
      "<h2>"  // This is already HTML.
      + StringEscapeUtils.escapeHtml(message)  // plain text -> innocuous HTML
      + "</h2>"  // Also already HTML.
      );
}
}