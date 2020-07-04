class GhCcfda69feffd04cb3280ed4c87e420ef446777839_2{
@Override
    public void onHeadExtraAddons() throws JspException {
        if (!getCurrentList().isEmpty()) {
            StringBuilder stringBuild = new StringBuilder();
            HtmlTag input = new HtmlTag("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("id", makeSelectionId(listName));
            input.setAttribute("name", makeSelectionLabel(listName));
            input.setAttribute("value", StringEscapeUtils.escapeHtml(pageContext
                    .getRequest().getParameter(makeSelectionLabel(listName))));
            stringBuild.append(input.render());
            ListTagUtil.write(pageContext, stringBuild.toString());
        }
    }
}