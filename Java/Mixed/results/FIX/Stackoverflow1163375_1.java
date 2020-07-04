class Stackoverflow1163375_1{
void vulnerable(){
session.invalidate();
session=request.getSession(true); 
}
}