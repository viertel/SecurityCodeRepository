class Stackoverflow1081242_1{
void vulnerable(){
PreparedStatement pstmt = connection.prepareStatement(
"INSERT INTO USERS ( USER_ID, FIRST_NAME, LAST_NAME, SEX, DATE ) " +
" values (?, ?, ?, ?, ? )");

pstmt.setString( 1, userId );
pstmt.setString( 3, myUser.getLastName() ); 
pstmt.setString( 2, myUser.getFirstName() ); // please use "getFir…" instead of "GetFir…", per Java conventions.
pstmt.setString( 4, myUser.getSex() );
java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() ); // Notice the ".sql." (not "util") in package name.
pstmt.setDate( 5, sqlDate ); 
}
}