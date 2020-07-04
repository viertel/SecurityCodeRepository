class GhC3a56b2efcf7a5264d19c183ac520e073f6686555_4{
MultipartStream(InputStream input,
             byte[] boundary,
             int bufSize,
             ProgressNotifier pNotifier) {
         this.input = input;
         this.bufSize = bufSize;
         this.buffer = new byte[bufSize];
         this.notifier = pNotifier;

        // We prepend CR/LF to the boundary to chop trailng CR/LF from
        // body-data tokens.
        this.boundaryLength = boundary.length + BOUNDARY_PREFIX.length;
        if (bufSize < this.boundaryLength + 1) {
            throw new IllegalArgumentException(
                "The buffer size specified for the MultipartStream is too small");
        }
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