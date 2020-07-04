class Stackoverflow37272761_1{
private Socket removeSSLv2v3(Socket socket) {

    if (!(socket instanceof SSLSocket)) {
        return socket;
    }

    SSLSocket sslSocket = (SSLSocket) socket;

    String[] protocols = sslSocket.getEnabledProtocols();
    Set<String> set = new HashSet<String>();
    for (String s : protocols) {
        if (s.equals("SSLv3") || s.equals("SSLv2Hello")) {
            continue;
        }
        set.add(s);
    }
    sslSocket.setEnabledProtocols(set.toArray(new String[0]));

    return sslSocket;
}
}