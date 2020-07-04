class GhC97d359dd8a33d157fb70846d77e6ef759134d6bc_2{
private String getSortDir() {

        String sortDirectionKey = ListTagUtil.makeSortDirLabel(getListName());
        String sortDir = pageContext.getRequest().getParameter(sortDirectionKey);
        if (StringUtils.isBlank(sortDir)) {
            if (RequestContext.SORT_DESC.equals(defaultSortDir)) {
                return RequestContext.SORT_DESC;
            }
            return RequestContext.SORT_ASC;
        }
        else {
            if (sortDir.equals(RequestContext.SORT_ASC)) {
                return RequestContext.SORT_ASC;
            }
            else {
                return RequestContext.SORT_DESC;
            }
        }
    }
}