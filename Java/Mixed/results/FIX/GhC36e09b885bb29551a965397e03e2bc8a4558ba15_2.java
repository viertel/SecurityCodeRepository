class GhC36e09b885bb29551a965397e03e2bc8a4558ba15_2{
void vulnerable(){
if (JNLPRuntime.isVerifying()) {

            JarSigner js;
            waitForJars(initialJars); //download the jars first.

            try {
                js = verifyJars(initialJars);
            } catch (Exception e) {
                //we caught an Exception from the JarSigner class.
                //Note: one of these exceptions could be from not being able
                //to read the cacerts or trusted.certs files.
                e.printStackTrace();
                throw new LaunchException(null, null, R("LSFatal"),
                        R("LCInit"), R("LFatalVerification"), R("LFatalVerificationInfo"));
            }

            //Case when at least one jar has some signing
            if (js.anyJarsSigned() && js.isFullySignedByASingleCert()) {
                signing = true;

                if (!js.allJarsSigned() &&
                        !SecurityWarning.showNotAllSignedWarningDialog(file))
                    throw new LaunchException(file, null, R("LSFatal"), R("LCClient"), R("LSignedAppJarUsingUnsignedJar"), R("LSignedAppJarUsingUnsignedJarInfo"));

                //user does not trust this publisher
                if (!js.getAlreadyTrustPublisher()) {
                    checkTrustWithUser(js);
                } else {
                    /**
                     * If the user trusts this publisher (i.e. the publisher's certificate
                     * is in the user's trusted.certs file), we do not show any dialogs.
                     */
                }
            } else {

                signing = false;
                //otherwise this jar is simply unsigned -- make sure to ask
                //for permission on certain actions
            }
        }

        for (JARDesc jarDesc : file.getResources().getJARs()) {
            try {
                File cachedFile = tracker.getCacheFile(jarDesc.getLocation());

                if (cachedFile == null) {
                    System.err.println("JAR " + jarDesc.getLocation() + " not found. Continuing.");
                    continue; // JAR not found. Keep going.
                }

                // TODO: Should be toURI().toURL()
                URL location = cachedFile.toURL();
                SecurityDesc jarSecurity = file.getSecurity();

                if (file instanceof PluginBridge) {

                    URL codebase = null;

                    if (file.getCodeBase() != null) {
                        codebase = file.getCodeBase();
                    } else {
                        //Fixme: codebase should be the codebase of the Main Jar not
                        //the location. Although, it still works in the current state.
                        codebase = file.getResources().getMainJAR().getLocation();
                    }
                    if (signing) {
                        jarSecurity = new SecurityDesc(file,
                                SecurityDesc.ALL_PERMISSIONS,
                                codebase.getHost());
                    } else {
                        jarSecurity = new SecurityDesc(file,
                                SecurityDesc.SANDBOX_PERMISSIONS,
                                codebase.getHost());
                    }
                }

                jarLocationSecurityMap.put(location, jarSecurity);
            } catch (MalformedURLException mfe) {
                System.err.println(mfe.getMessage());
            }
        }
        activateJars(initialJars);
}
}