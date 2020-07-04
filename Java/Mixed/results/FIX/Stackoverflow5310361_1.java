class Stackoverflow5310361_1{
void vulnerable(){
MessageDigest digest = MessageDigest.getInstance("SHA-256");
digest.update(password.getBytes());
digest.update(salt);
byte[] raw = digest.digest();
//significantly slow down a brute force attack
for (int i = 0; i < 34000; i++) {
    digest.reset();
    digest.update(raw);
    raw = digest.digest();
}
SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
}
}