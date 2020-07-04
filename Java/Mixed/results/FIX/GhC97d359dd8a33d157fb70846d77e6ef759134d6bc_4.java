class GhC97d359dd8a33d157fb70846d77e6ef759134d6bc_4{
public void sort() {

        String sortKey = ListTagUtil.makeSortByLabel(uniqueName);
        String sortDirectionKey = ListTagUtil.makeSortDirLabel(uniqueName);
        String sortAttribute = StringUtils.defaultString(request.getParameter(sortKey));
        String sortDir = StringUtils.defaultString(request.getParameter(sortDirectionKey));
        if (!sortDir.equals(RequestContext.SORT_ASC) &&
                !sortDir.equals(RequestContext.SORT_DESC)) {
            sortDir = ascending ? RequestContext.SORT_ASC : RequestContext.SORT_DESC;
        }
        if (AlphaBarHelper.getInstance().isSelected(uniqueName, request)) {
            Collections.sort(dataset, new DynamicComparator(alphaCol,
                    RequestContext.SORT_ASC));
        }
        else if (!StringUtils.isBlank(sortAttribute)) {
            try {
                Collections.sort(dataset, new DynamicComparator(sortAttribute, sortDir));
            }
            catch (IllegalArgumentException iae) {
                Collections.sort(dataset, new DynamicComparator(defaultSortAttribute,
                        sortDir));
            }
        }
        else if (!StringUtils.isBlank(defaultSortAttribute)) {
            Collections.sort(dataset, new DynamicComparator(defaultSortAttribute,
                     ascending ? RequestContext.SORT_ASC : RequestContext.SORT_DESC));
         }
     }
}