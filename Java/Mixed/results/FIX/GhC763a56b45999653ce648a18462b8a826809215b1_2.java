class GhC763a56b45999653ce648a18462b8a826809215b1_2{
@Override
    public String toString() {

        StringBuilder sb = new StringBuilder("User username=\"");
        sb.append(RequestUtil.filter(username));
        sb.append("\"");
         sb.append("/>");
         return (sb.toString());

    }
}