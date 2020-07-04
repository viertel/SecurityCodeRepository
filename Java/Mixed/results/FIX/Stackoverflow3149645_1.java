class Stackoverflow3149645_1{
public static String html2text(String html) {
    return Jsoup.parse(html).text();
}
}