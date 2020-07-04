class GhCeac689abaf43f31cb5afaaf93aa80009ede925d3_2{
private static String extractCN(final String subjectPrincipal) throws SSLException {
		if (subjectPrincipal == null) {
			return null;
		}
		try {
			final LdapName subjectDN = new LdapName(subjectPrincipal);
			final List<Rdn> rdns = subjectDN.getRdns();
			for (int i = rdns.size() - 1; i >= 0; i--) {
				final Rdn rds = rdns.get(i);
				final Attributes attributes = rds.toAttributes();
				final Attribute cn = attributes.get("cn");
				if (cn != null) {
					try {
						final Object value = cn.get();
						if (value != null) {
							return value.toString();
						}
					} catch (NoSuchElementException ignore) {
					} catch (NamingException ignore) {
					}
				}
			}
		} catch (InvalidNameException e) {
			throw new SSLException(subjectPrincipal + " is not a valid X500 distinguished name");
		}
		return null;
	}
}