class GhCb4462cc92fc5ba12ee624ba6cf0d3ae91a81358e_2{
    public static void unjar(InputStream in, File dest) throws IOException {
        if (!dest.exists()) {
            dest.mkdirs();
        }
        if (!dest.isDirectory()) {
            throw new IOException("Destination must be a directory.");
        }
        JarInputStream jin = new JarInputStream(in);
        byte[] buffer = new byte[1024];
        String canonicalDocBasePrefix = dest.getCanonicalPath();
        if (!canonicalDocBasePrefix.endsWith(File.separator)) {
            canonicalDocBasePrefix += File.separator;
        }
        ZipEntry entry = jin.getNextEntry();
        while (entry != null) {
            String fileName = entry.getName();
            if (fileName.charAt(fileName.length() - 1) == '/') {
                fileName = fileName.substring(0, fileName.length() - 1);
            }
            if (fileName.charAt(0) == '/') {
                fileName = fileName.substring(1);
            }
            if (File.separatorChar != '/') {
                fileName = fileName.replace('/', File.separatorChar);
            }
            File file = new File(dest, fileName);
            if (!file.getCanonicalPath().startsWith(canonicalDocBasePrefix)) {
                throw new IOException("illegalPath: " + fileName);
            }
            if (entry.isDirectory()) {
                // make sure the directory exists
                file.mkdirs();
                jin.closeEntry();
            } else {
                // make sure the directory exists
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                // dump the file
                OutputStream out = new FileOutputStream(file);
                int len = 0;
                while ((len = jin.read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                jin.closeEntry();
                file.setLastModified(entry.getTime());
            }
            entry = jin.getNextEntry();
        }
        jin.close();
    }
}