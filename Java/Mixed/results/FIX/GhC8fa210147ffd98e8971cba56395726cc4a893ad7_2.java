class GhC8fa210147ffd98e8971cba56395726cc4a893ad7_2{
static {
        webDigesters[0] = DigesterFactory.newDigester(false, false, webRuleSet);
        webDigesters[0].getParser();
        webFragmentDigesters[0] = DigesterFactory.newDigester(false, false,
                webFragmentRuleSet);
        webFragmentDigesters[0].getParser();

        webDigesters[1] = DigesterFactory.newDigester(true, false, webRuleSet);
        webDigesters[1].getParser();
        webFragmentDigesters[1] = DigesterFactory.newDigester(true, false,
                webFragmentRuleSet);
        webFragmentDigesters[1].getParser();

        webDigesters[2] = DigesterFactory.newDigester(false, true, webRuleSet);
        webDigesters[2].getParser();
        webFragmentDigesters[2] = DigesterFactory.newDigester(false, true,
                webFragmentRuleSet);
        webFragmentDigesters[2].getParser();

        webDigesters[3] = DigesterFactory.newDigester(true, true, webRuleSet);
        webDigesters[3].getParser();
        webFragmentDigesters[3] = DigesterFactory.newDigester(true, true,
                webFragmentRuleSet);
        webFragmentDigesters[3].getParser();
    }    
protected void createWebXmlDigester(boolean namespaceAware,
            boolean validation) {

        if (!namespaceAware && !validation) {
            webDigester = webDigesters[0];
            webFragmentDigester = webFragmentDigesters[0];

        } else if (!namespaceAware && validation) {
            webDigester = webDigesters[1];
            webFragmentDigester = webFragmentDigesters[1];

        } else if (namespaceAware && !validation) {
            webDigester = webDigesters[2];
            webFragmentDigester = webFragmentDigesters[2];

        } else {
            webDigester = webDigesters[3];
            webFragmentDigester = webFragmentDigesters[3];
        }
     }
}