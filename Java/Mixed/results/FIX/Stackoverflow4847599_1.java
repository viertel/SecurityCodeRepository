class Stackoverflow4847599_1{
private static Policy policy;
    private static AntiSamy antiSamy;

    private static AntiSamy getAntiSamy() throws PolicyException  {
        if (antiSamy == null) {
            policy = getPolicy("evocatus-default");
            antiSamy = new AntiSamy();
        }
        return antiSamy;

    }

    public static String sanitize(String input) {
        CleanResults cr;
        try {
            cr = getAntiSamy().scan(input, policy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cr.getCleanHTML();
    }

    private static Policy getPolicy(String name) throws PolicyException {
        Policy policy = 
            Policy.getInstance(Policy.class.getResourceAsStream("/META-INF/antisamy/" + name + ".xml"));
        return policy;
    }
}