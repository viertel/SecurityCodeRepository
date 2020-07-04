class GhC5971f9392edc6d70808b2599b062b050fcd11d23_2{
private static String localeToString(Locale locale) {
        if (locale != null) {
            return escapeXml(locale.toString());//locale.getDisplayName();
        } else {
            return "";
        }
     }
}