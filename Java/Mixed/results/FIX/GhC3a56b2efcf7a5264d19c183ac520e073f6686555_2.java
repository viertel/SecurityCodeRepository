class GhC3a56b2efcf7a5264d19c183ac520e073f6686555_2{
/**
          * Creates a new instance.
          * @param ctx The request context.
          * @throws FileUploadException An error occurred while
          *   parsing the request.
          * @throws IOException An I/O error occurred.
          */
         FileItemIteratorImpl(RequestContext ctx)
                 throws FileUploadException, IOException {
             if (ctx == null) {
                 throw new NullPointerException("ctx parameter");
             }
 
             String contentType = ctx.getContentType();
             if ((null == contentType)
                     || (!contentType.toLowerCase().startsWith(MULTIPART))) {
                 throw new InvalidContentTypeException(
                         "the request doesn't contain a "
                         + MULTIPART_FORM_DATA
                         + " or "
                         + MULTIPART_MIXED
                         + " stream, content type header is "
                         + contentType);
             }
 
             InputStream input = ctx.getInputStream();
 
             if (sizeMax >= 0) {
                 int requestSize = ctx.getContentLength();
                 if (requestSize == -1) {
                     input = new LimitedInputStream(input, sizeMax) {
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
 
             String charEncoding = headerEncoding;
             if (charEncoding == null) {
                 charEncoding = ctx.getCharacterEncoding();
             }
 
             boundary = getBoundary(contentType);
             if (boundary == null) {
                 throw new FileUploadException(
                         "the request was rejected because "
                         + "no multipart boundary was found");
             }

            notifier = new MultipartStream.ProgressNotifier(listener,
                    ctx.getContentLength());
            try {
                multi = new MultipartStream(input, boundary, notifier);
            } catch (IllegalArgumentException iae) {
                throw new InvalidContentTypeException(
                        "The boundary specified in the Content-type header is too long", iae);
            }
            multi.setHeaderEncoding(charEncoding);

            skipPreamble = true;
             findNextItem();
         }
}