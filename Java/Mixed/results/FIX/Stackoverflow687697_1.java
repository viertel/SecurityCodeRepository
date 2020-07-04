class Stackoverflow687697_1{
void vulnerable(){
try{
      PreparedStatement ps = connection.prepareStatement("some sql");
      ps.setString(1, "foobar1");
      ps.setString(2, "foobar2");
      ps.execute();
 }finally {
     ps.close();

 }
}
}