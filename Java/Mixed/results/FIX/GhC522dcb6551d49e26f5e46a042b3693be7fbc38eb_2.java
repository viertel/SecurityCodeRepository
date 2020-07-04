class GhC522dcb6551d49e26f5e46a042b3693be7fbc38eb_2{
protected byte[] doDownload(String url, boolean isProxySet,
                                String proxyHost, int proxyPort) {

        AndroidHttpClient client = null;
        try {
            client = AndroidHttpClient.newInstance(mUserAgent);
            HttpUriRequest req = new HttpGet(url);
            HttpResponse response = client.execute(req);
            HttpEntity entity = response.getEntity();
            byte[] body = null;
            if (entity != null) {
                try {
                    long contentLength = entity.getContentLength();
                    if (contentLength > 0 && contentLength <= MAXIMUM_CONTENT_LENGTH_BYTES) {
                        body = new byte[(int) contentLength];
                        DataInputStream dis = new DataInputStream(entity.getContent());
                        try {
                            dis.readFully(body);
                        } finally {
                            try {
                                dis.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Unexpected IOException.", e);
                            }
                        }
                    }
                } finally {
                    if (entity != null) {
                        entity.consumeContent();
                    }
                }
            }
            return body;
        } catch (Exception e) {
            if (DEBUG) Log.d(TAG, "error " + e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }
}