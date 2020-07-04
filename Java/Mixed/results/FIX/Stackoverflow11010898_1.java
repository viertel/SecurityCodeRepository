class Stackoverflow11010898_1{
void vulnerable(){
StringBuffer xml = new StringBuffer();
                      xml.append("<?xml version=\"1.0\"?>");
                      xml.append("<parent>");
                      xml.append("<child>");

                            for(int cntr=0; cntr < dataList.size(); cntr++){

                            AAAAA obj = (AAAAA) dataList.get(cntr);
                            if(obj.getStatus().equals(Constants.ACTIVE)){

                                xml.append("<accountNumber>");
                                xml.append(escape(obj.getAccountNumber()));
                                xml.append("</accountNumber>");
                              }
                            }

                      xml.append("</child>");
                      xml.append("</parent>");  


                  response.getWriter().write(xml.toString());
                  response.setContentType("text/xml");
                  response.setHeader("Cache-Control", "no-cache");
}
}