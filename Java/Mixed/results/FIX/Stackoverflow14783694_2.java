class Stackoverflow14783694_2{
void vulnerable(){
final String html = "<b><script>your script here</script></b>";
Document doc = Jsoup.parse(html);

for( Element element : doc.select("script") )
{
    element.replaceWith(TextNode.createFromEncoded(element.toString(), null));
}

System.out.println(doc);
}
}