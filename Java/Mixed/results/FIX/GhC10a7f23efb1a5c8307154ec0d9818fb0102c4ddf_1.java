class GhC10a7f23efb1a5c8307154ec0d9818fb0102c4ddf_1{
@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		// This is a temporary guard against CVE-2016-5007.
		// Should be removed after upgrading to Spring MVC 4.3.1+ and Spring Security 4.1.1+.
		// See also: http://pivotal.io/security/cve-2016-5007
		AntPathMatcher pathMatcher = new AntPathMatcher();
		pathMatcher.setTrimTokens(false);
		configurer.setPathMatcher(pathMatcher);

		// If enabled a method mapped to "/users" also matches to "/users/"
		configurer.setUseTrailingSlashMatch(false);
		// If enabled a method mapped to "/users" also matches to "/users.*"
		configurer.setUseSuffixPatternMatch(false);
	}
}