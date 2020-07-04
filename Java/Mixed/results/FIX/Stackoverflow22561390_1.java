class Stackoverflow22561390_1{
//1. Generate new CSRF token and add it to user once on login and store user in http session.
//this code is in the DefaultUser implementation of ESAPI
/** This user's CSRF token. */
private String csrfToken = resetCSRFToken();
//...
public String resetCSRFToken() {
    csrfToken = ESAPI.randomizer().getRandomString(8, DefaultEncoder.CHAR_ALPHANUMERICS);
    return csrfToken;
}

//2. On any forms or urls that should be protected, add the token as a parameter / hidden field.
final static String CSRF_TOKEN_NAME = "ctoken";
//this code is from the DefaultHTTPUtilities implementation in ESAPI
public String addCSRFToken(String href) {
    User user = ESAPI.authenticator().getCurrentUser();
    if (user.isAnonymous()) {
        return href;
    }
    // if there are already parameters append with &, otherwise append with ?
    String token = CSRF_TOKEN_NAME + "=" + user.getCSRFToken();
    return href.indexOf( '?') != -1 ? href + "&" + token : href + "?" + token;
}
//...
public String getCSRFToken() {
    User user = ESAPI.authenticator().getCurrentUser();
    if (user == null) return null;
    return user.getCSRFToken();
}

//3. On the server side for those protected actions, check that the submitted token matches the token from the user object in the session.
//this code is from the DefaultHTTPUtilities implementation in ESAPI
public void verifyCSRFToken(HttpServletRequest request) throws IntrusionException {
    User user = ESAPI.authenticator().getCurrentUser();
    // check if user authenticated with this request - no CSRF protection required
    if( request.getAttribute(user.getCSRFToken()) != null ) {
        return;
    }
    String token = request.getParameter(CSRF_TOKEN_NAME);
    if ( !user.getCSRFToken().equals( token ) ) {
        throw new IntrusionException("Authentication failed", "Possibly forged HTTP request without proper CSRF token detected");
    }
}

//4. On logout and session timeout, the user object is removed from the session and the session destroyed.
//this code is in the DefaultUser implementation of ESAPI
public void logout() {
    ESAPI.httpUtilities().killCookie( ESAPI.currentRequest(), ESAPI.currentResponse(), HTTPUtilities.REMEMBER_TOKEN_COOKIE_NAME );
    HttpSession session = ESAPI.currentRequest().getSession(false);
    if (session != null) {
        removeSession(session);
        session.invalidate();
    }
    ESAPI.httpUtilities().killCookie(ESAPI.currentRequest(), ESAPI.currentResponse(), "JSESSIONID");
    loggedIn = false;
    logger.info(Logger.SECURITY_SUCCESS, "Logout successful" );
    ESAPI.authenticator().setCurrentUser(User.ANONYMOUS);
}
}