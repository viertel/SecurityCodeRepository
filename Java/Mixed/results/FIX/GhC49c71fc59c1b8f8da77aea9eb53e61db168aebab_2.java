class GhC49c71fc59c1b8f8da77aea9eb53e61db168aebab_2{
void vulnerable(){
args = new Object[7];
                args[0] = response.encodeURL
                    (request.getContextPath() +
                     "/html/start?name=" +
                     URLEncoder.encode(hostName, "UTF-8"));
                args[1] = hostsStart;
                args[2] = response.encodeURL
                    (request.getContextPath() +
                     "/html/stop?name=" +
                     URLEncoder.encode(hostName, "UTF-8"));
                args[3] = hostsStop;
                args[4] = response.encodeURL
                    (request.getContextPath() +
                     "/html/remove?name=" +
                     URLEncoder.encode(hostName, "UTF-8"));
                args[5] = hostsRemove;
                args[6] = RequestUtil.filter(hostName);
                if (host == this.host) {
                    writer.print(MessageFormat.format(
                        MANAGER_HOST_ROW_BUTTON_SECTION, args));
                 } else {
                     writer.print(MessageFormat.format(
                         HOSTS_ROW_BUTTON_SECTION, args));
                 }
}
}