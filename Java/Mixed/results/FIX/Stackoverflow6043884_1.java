class Stackoverflow6043884_1{
void vulnerable(){
//If you use Spring Security, the framework should change the session id after login by default. Alternative:
HttpSession session = request.getSession();
    Map<String,Object> values = session.GetAll(); //This line is psydo code
    //Use getValueNames() and a loop with getValue(String name);

    // Kill the current session
   session.invalidate();

   HttpSession newSession = request.getSession(true);
   newSession.putAllValues(values); //This line is psydo code
}
}