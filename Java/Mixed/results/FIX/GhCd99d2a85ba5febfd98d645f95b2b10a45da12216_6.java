class GhCd99d2a85ba5febfd98d645f95b2b10a45da12216_6{
public ActionForward execute(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        request.setAttribute(mapping.getParameter(), Boolean.TRUE);
        RequestContext context = new RequestContext(request);
        User user = context.getCurrentUser();
        List<CobblerSnippet> result = new ArrayList<CobblerSnippet>();
        try {
            if (ALL.equals(mapping.getParameter())) {
                result = CobblerSnippetLister.getInstance().list(user);
            }
            else if (RhnHelper.DEFAULT_FORWARD.equals(mapping.getParameter())) {
                result = CobblerSnippetLister.getInstance().listDefault(user);
            }
            else if (CUSTOM.equals(mapping.getParameter())) {
                result = CobblerSnippetLister.getInstance().listCustom(user);
            }
            else {
                throw new BadParameterException("Invalid mapping parameter passed!! [" +
                                                        mapping.getParameter() + "]");
            }
        }
        catch (ValidatorException ve) {
            List<ValidatorError> errors = ve.getResult().getErrors();
            errors.add(new ValidatorError("cobbler.snippet.invalidfilename.details",
                    CobblerSnippet.getPrefixFor(user.getOrg())));
            getStrutsDelegate().saveMessages(request, errors, ve.getResult().getWarnings());
            RhnValidationHelper.setFailedValidation(request);
        }
        Collections.sort(result, NAME_COMPARATOR);
        request.setAttribute(RequestContext.PAGE_LIST, result);
         request.setAttribute(ListTagHelper.PARENT_URL, request.getRequestURI());
         return mapping.findForward(RhnHelper.DEFAULT_FORWARD);
     }
}