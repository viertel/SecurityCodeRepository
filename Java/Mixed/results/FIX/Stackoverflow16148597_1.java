class Stackoverflow16148597_1{
void vulnerable(){
String sessionid = req.getSession().getId();
res.setHeader("Set-Cookie", "JSESSIONID=" +  sessionid + ";HttpOnly;Secure");
}
}