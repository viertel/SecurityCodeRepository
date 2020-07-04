class Stackoverflow31786859_1{
void vulnerable(){
KeyStore.Entry newEntry = new KeyStore.TrustedCertificateEntry(someCert);
ks.setEntry("someAlias", newEntry, null);
}
}