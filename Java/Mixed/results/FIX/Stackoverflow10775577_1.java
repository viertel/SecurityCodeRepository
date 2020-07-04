class Stackoverflow10775577_1{
private static final String ALGORITHM = "Blowfish/CBC/PKCS5Padding";
/* now returns the IV that was used */
private static byte[] encrypt(SecretKey key, 
                              InputStream is, 
                              OutputStream os) {
    try {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        doCopy(cis, os);
        return cipher.getIV();
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
}

private static void decrypt(SecretKey key, 
                            byte[] iv, 
                            InputStream is, 
                            OutputStream os) 
{
    try {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        doCopy(cis, os);
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
}
}