class GhC6ff5753bb10a6ab9c5f24469918e2f9c945503fe_4{
   MultipartStream(InputStream input,
            byte[] boundary,
            int bufSize,
            ProgressNotifier pNotifier) throws IllegalArgumentException {

        if (boundary == null) {
            throw new IllegalArgumentException("boundary may not be null");
        }
        // We prepend CR/LF to the boundary to chop trailing CR/LF from
        // body-data tokens.
        this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;
        if (bufSize < this.boundaryLength + 1) {
            throw new IllegalArgumentException(
                    "The buffer size specified for the MultipartStream is too small");
        }

        this.input = input;
        this.bufSize = Math.max(bufSize, boundaryLength*2);
        this.buffer = new byte[this.bufSize];
        this.notifier = pNotifier;

        this.boundary = new byte[this.boundaryLength];
        this.keepRegion = this.boundary.length;

        System.arraycopy(BOUNDARY_PREFIX, 0, this.boundary, 0,
                BOUNDARY_PREFIX.length);
        System.arraycopy(boundary, 0, this.boundary, BOUNDARY_PREFIX.length,
                 boundary.length);
 
         head = 0;
         tail = 0;
     }
}