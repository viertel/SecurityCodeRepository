class GhCf554b688189ef81f2a1b7d66209c3d733acc403a_2{
public ActionForward execute(ActionMapping mapping,
                                 ActionForm formIn,
                                 HttpServletRequest request,
                                HttpServletResponse response) {
       String catalinaBase = System.getProperty("catalina.base");
       String contents = FileUtils.getTailOfFile(catalinaBase + "/logs/catalina.out", 1000);
       contents = StringEscapeUtils.escapeHtml(contents);
       request.setAttribute("contents", contents);
       return mapping.findForward(RhnHelper.DEFAULT_FORWARD);
   }
}