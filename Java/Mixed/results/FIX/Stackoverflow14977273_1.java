class Stackoverflow14977273_1{
void vulnerable(){
userName= req.getParameter(Constant.USERNAME);
if(userName.matches("[0-9a-zA-Z_]+"))
     session.setAttribute(Constant.USERNAME, userName);
}
}