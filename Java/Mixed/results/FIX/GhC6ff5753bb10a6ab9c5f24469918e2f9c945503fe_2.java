class GhC6ff5753bb10a6ab9c5f24469918e2f9c945503fe_2{
/**
     * Creates a new instance.
     *
     * @param ctx The request context.
     * @throws FileUploadException An error occurred while
     *                             parsing the request.
     * @throws IOException         An I/O error occurred.
     */
    FileItemIteratorImpl(RequestContext ctx)
            throws FileUploadException, IOException {
        if (ctx == null) {
            throw new NullPointerException("ctx parameter");
        }

        String contentType = ctx.getContentType();
        if ((null == contentType)
                || (!contentType.toLowerCase(Locale.ENGLISH).startsWith(MULTIPART))) {
            throw new InvalidContentTypeException(
                    "the request doesn't contain a "
                            + MULTIPART_FORM_DATA
                            + " or "
                            + MULTIPART_MIXED
                            + " stream, content type header is "
                            + contentType);
        }

        InputStream input = null; // N.B. this is eventually closed in MultipartStream processing

        if (sizeMax >= 0) {
            int requestSize = ctx.getContentLength();
            if (requestSize == -1) {
                input = new LimitedInputStream(ctx.getInputStream(), sizeMax) {
                    @Override
                    protected void raiseError(long pSizeMax, long pCount)
                            throws IOException {
                        FileUploadException ex =
                                new SizeLimitExceededException(
                                        "the request was rejected because"
                                                + " its size (" + pCount
                                                + ") exceeds the configured maximum"
                                                + " (" + pSizeMax + ")",
                                        pCount, pSizeMax);
                        throw new FileUploadIOException(ex);
                    }
                };
            } else {
                if (sizeMax >= 0 && requestSize > sizeMax) {
                    throw new SizeLimitExceededException(
                            "the request was rejected because its size ("
                                    + requestSize
                                    + ") exceeds the configured maximum ("
                                    + sizeMax + ")",
                            requestSize, sizeMax);
                }
            }
        }

        if (input == null) {
            input = ctx.getInputStream();
        }

        String charEncoding = headerEncoding;
        if (charEncoding == null) {
            charEncoding = ctx.getCharacterEncoding();
        }

        boundary = getBoundary(contentType);
        if (boundary == null) {
            IOUtils.closeQuietly(input); // avoid possible resource leak
            throw new FileUploadException(
                    "the request was rejected because "
                            + "no multipart boundary was found");
        }

        notifier = new MultipartStream.ProgressNotifier(listener,
                ctx.getContentLength());
        try {
            multi = new MultipartStream(input, boundary, notifier);
        } catch (IllegalArgumentException iae) {
            IOUtils.closeQuietly(input); // avoid possible resource leak
            throw new InvalidContentTypeException(
                    String.format("The boundary specified in the %s header is too long", CONTENT_TYPE), iae);
        }

        multi.setHeaderEncoding(charEncoding);

        skipPreamble = true;
        findNextItem();
    }
}