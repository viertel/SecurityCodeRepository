class Stackoverflow11096421_1{
void vulnerable(){
String id = request.getParameter("id") != null ? request.getParameter("id") : "0";
    aaaa doc = bbb.getdetailsById(id);    
    byte b[] = doc.getUploaded();        
        response.setContentType("APPLICATION/OCTET-STREAM");
        String disHeader = "Attachment;Filename=" + doc.getName();
        response.setHeader("Content-Disposition", disHeader);
        servletoutputstream = response.getOutputStream();
        servletoutputstream.write(getFormatedString(b), 0, b.length);
}

public static byte[] getFormatedString(byte[] string){

    String str=new String(string);
    str=HtmlUtils.htmlEscape(str);
    return str.getBytes();

}
}