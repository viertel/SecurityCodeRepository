class GhC36ea46fd8ddea62bfcdf004b03d78d0d814004f8_4{
private static final SecureEntityResolver secureEntityResolver;
static {
         urlEncoder = new URLEncoder();
         urlEncoder.addSafeCharacter('-');
         urlEncoder.addSafeCharacter('_');
         urlEncoder.addSafeCharacter('.');
        urlEncoder.addSafeCharacter('*');
        urlEncoder.addSafeCharacter('/');
        if (Globals.IS_SECURITY_ENABLED) {
            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            secureEntityResolver = new SecureEntityResolver();
        } else {
            factory = null;
            secureEntityResolver = null;
        }
    }
}