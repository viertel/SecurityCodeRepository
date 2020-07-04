class Stackoverflow2311467_1{
void vulnerable(){
request.getSession(false).invalidate();
session = getSession(true);
}
}