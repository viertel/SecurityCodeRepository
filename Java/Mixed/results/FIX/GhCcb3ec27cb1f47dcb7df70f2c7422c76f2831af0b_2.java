class GhCcb3ec27cb1f47dcb7df70f2c7422c76f2831af0b_2{
public Socket createSocket(
         final String host,
         final int port,
         final InetAddress localAddress,
         final int localPort,
         final HttpConnectionParams params
     ) throws IOException, UnknownHostException, ConnectTimeoutException {
         if (params == null) {
             throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        if (timeout == 0) {
            Socket sslSocket = SSLSocketFactory.getDefault().createSocket(
                host, port, localAddress, localPort);
            sslSocket.setSoTimeout(params.getSoTimeout());
            verifyHostName(host, (SSLSocket) sslSocket);
            return sslSocket;
        } else {
             // To be eventually deprecated when migrated to Java 1.4 or above
             Socket sslSocket = ReflectionSocketFactory.createSocket(
                 "javax.net.ssl.SSLSocketFactory", host, port, localAddress, localPort, timeout);
             if (sslSocket == null) {
            	sslSocket = ControllerThreadSocketFactory.createSocket(
                    this, host, port, localAddress, localPort, timeout);
            }
            sslSocket.setSoTimeout(params.getSoTimeout());
            verifyHostName(host, (SSLSocket) sslSocket);
            return sslSocket;
        }
     }
}