class Stackoverflow28707227_1{
void vulnerable(){
String safeUserName = StringUtils.replaceEach(userName, new String[] {"\n", "\r"}, new String[] {"", ""});
String safePassword = StringUtils.replaceEach(password, new String[] {"\n", "\r"}, new String[] {"", ""});
Cookie newloginCookie = new Cookie("CMCLoginCookie", safeUserName + ":" + safePassword);
                                newloginCookie.setMaxAge(24 * 60 * 60 * 1000);
                                response.addCookie(newloginCookie);
}
}