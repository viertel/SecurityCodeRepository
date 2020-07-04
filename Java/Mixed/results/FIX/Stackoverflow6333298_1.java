class Stackoverflow6333298_1{
void vulnerable(){
Policy policy = Policy.getInstance("antisamy.xml");
        ResourceBundle messages = ResourceBundle.getBundle("AntiSamy", Locale.getDefault());

        CssScanner scanner = new CssScanner(policy, messages);
        CleanResults results = scanner.scanStyleSheet(stylesheet, Integer.MAX_VALUE);
}
}