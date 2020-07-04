class GhC3dd814be144b66d4881cb7e9ea6228da3f148f52_2{
public void get(String src, String dst,
                    SftpProgressMonitor monitor, int mode) throws SftpException {
        // System.out.println("get: "+src+" "+dst);

        boolean _dstExist = false;
        String _dst = null;
        try {
            ((MyPipedInputStream) io_in).updateReadSide();

            src = remoteAbsolutePath(src);
            dst = localAbsolutePath(dst);

            Vector v = glob_remote(src);
            int vsize = v.size();
            if (vsize == 0) {
                throw new SftpException(SSH_FX_NO_SUCH_FILE, "No such file");
            }

            File dstFile = new File(dst);
            boolean isDstDir = dstFile.isDirectory();
            StringBuffer dstsb = null;
            if (isDstDir) {
                if (!dst.endsWith(file_separator)) {
                    dst += file_separator;
                }
                dstsb = new StringBuffer(dst);
            } else if (vsize > 1) {
                throw new SftpException(SSH_FX_FAILURE,
                        "Copying multiple files, but destination is missing or a file.");
            }

            for (int j = 0; j < vsize; j++) {
                String _src = (String) (v.elementAt(j));
                SftpATTRS attr = _stat(_src);
                if (attr.isDir()) {
                    throw new SftpException(SSH_FX_FAILURE,
                            "not supported to get directory " + _src);
                }

                _dst = null;
                if (isDstDir) {
                    int i = _src.lastIndexOf('/');
                    if (i == -1) dstsb.append(_src);
                    else dstsb.append(_src.substring(i + 1));
                    _dst = dstsb.toString();
                    if (_dst.indexOf("..") != -1) {
                        String dstc = (new java.io.File(dst)).getCanonicalPath();
                        String _dstc = (new java.io.File(_dst)).getCanonicalPath();
                        if (!(_dstc.length() > dstc.length() &&
                                _dstc.substring(0, dstc.length() + 1).equals(dstc + file_separator))) {
                            throw new SftpException(SSH_FX_FAILURE,
                                    "writing to an unexpected file " + _src);
                        }
                    }
                    dstsb.delete(dst.length(), _dst.length());
                } else {
                    _dst = dst;
                }

                File _dstFile = new File(_dst);
                FileOutputStream fos = null;
                _dstExist = _dstFile.exists();
                try {
                    if (mode == OVERWRITE) {
                        fos = new FileOutputStream(_dst);
                    } else {
                        fos = new FileOutputStream(_dst, true); // append
                    }
                    // System.err.println("_get: "+_src+", "+_dst);
                    _get(_src, fos, monitor, mode, new File(_dst).length());
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        } catch (Exception e) {
            if (!_dstExist && _dst != null) {
                File _dstFile = new File(_dst);
                if (_dstFile.exists() && _dstFile.length() == 0) {
                    _dstFile.delete();
                }
            }
        }
    }
}