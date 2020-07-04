class GhC2b349c7cf89f32057706fb637f857ee0f7540370_2{
/**
      * Does a post and reports back the status code.
      *
      * @throws IOException
      */
    private static String get(Uri pacUri) throws IOException {
        URL url = new URL(pacUri.toString());
        URLConnection urlConnection = url.openConnection(java.net.Proxy.NO_PROXY);
        long contentLength = -1;
        try {
            contentLength = Long.parseLong(urlConnection.getHeaderField("Content-Length"));
        } catch (NumberFormatException e) {
            // Ignore
        }
        if (contentLength > MAX_PAC_SIZE) {
            throw new IOException("PAC too big: " + contentLength + " bytes");
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count;
        while ((count = urlConnection.getInputStream().read(buffer)) != -1) {
            bytes.write(buffer, 0, count);
            if (bytes.size() > MAX_PAC_SIZE) {
                throw new IOException("PAC too big");
            }
        }
        return bytes.toString();
    }
}