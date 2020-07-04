class GhC0bdcb9d900eb665fe0246a5f85bc3a15199d8138_2{
private static <T> Unmarshaller createUnmarshaller(Class<T> clazz, boolean preserveWhitespace) {
         Unmarshaller u = new Unmarshaller(clazz);
        u.setIgnoreExtraAttributes(false);
        u.setIgnoreExtraElements(false);
        u.setWhitespacePreserve(preserveWhitespace);

        /*
         * Disable SAX features that allow XXE attacks
         * See:
         *   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-3004
         *   http://issues.opennms.org/browse/NMS-7291
         */
        u.setProperty(
            "org.exolab.castor.sax.features",
            "http://apache.org/xml/features/disallow-doctype-decl"
        );
        u.setProperty(
            "org.exolab.castor.sax.features-to-disable",
            "http://xml.org/sax/features/external-general-entities,http://xml.org/sax/features/external-parameter-entities,http://apache.org/xml/features/nonvalidating/load-external-dtd"
        );

        return u;
    }
}