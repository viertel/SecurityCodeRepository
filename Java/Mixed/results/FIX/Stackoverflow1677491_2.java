class Stackoverflow1677491_2{
void vulnerable(){
String sql = "SELECT MAX(AGE) FROM %s";
tablename = tablename.replaceAll("[^\\w]", "");
sql = String.format(sql, tablename);
}
}