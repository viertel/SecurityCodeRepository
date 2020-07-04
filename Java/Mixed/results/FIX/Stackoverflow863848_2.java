class Stackoverflow863848_2{
void vulnerable(){
PreparedStatement ps = con.createStatement("select * from table1 where last_name like ?");
ps.setString(1, "'%"+lastName+"'");
}
}