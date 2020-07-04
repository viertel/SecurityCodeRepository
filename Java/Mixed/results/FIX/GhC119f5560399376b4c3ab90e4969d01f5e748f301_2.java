class GhC119f5560399376b4c3ab90e4969d01f5e748f301_2{
int checkGrantUriPermissionLocked(int callingUid, String targetPkg, GrantUri grantUri,
             final int modeFlags, int lastTargetUid) {
         if (!Intent.isAccessUriMode(modeFlags)) {
             return -1;
         }
 
         if (targetPkg != null) {
             if (DEBUG_URI_PERMISSION) Slog.v(TAG_URI_PERMISSION,
                     "Checking grant " + targetPkg + " permission to " + grantUri);
         }
 
         final IPackageManager pm = AppGlobals.getPackageManager();
 
         // If this is not a content: uri, we can't do anything with it.
         if (!ContentResolver.SCHEME_CONTENT.equals(grantUri.uri.getScheme())) {
             if (DEBUG_URI_PERMISSION) Slog.v(TAG_URI_PERMISSION,
                     "Can't grant URI permission for non-content URI: " + grantUri);
            return -1;
        }

        // Bail early if system is trying to hand out permissions directly; it
        // must always grant permissions on behalf of someone explicit.
        final int callingAppId = UserHandle.getAppId(callingUid);
        if ((callingAppId == Process.SYSTEM_UID) || (callingAppId == Process.ROOT_UID)) {
            if ("com.android.settings.files".equals(grantUri.uri.getAuthority())) {
                // Exempted authority for cropping user photos in Settings app
            } else {
                Slog.w(TAG, "For security reasons, the system cannot issue a Uri permission"
                        + " grant to " + grantUri + "; use startActivityAsCaller() instead");
                return -1;
            }
        }

        final String authority = grantUri.uri.getAuthority();
        final ProviderInfo pi = getProviderInfoLocked(authority, grantUri.sourceUserId);
        if (pi == null) {
             Slog.w(TAG, "No content provider found for permission check: " +
                     grantUri.uri.toSafeString());
             return -1;
         }
 
         int targetUid = lastTargetUid;
         if (targetUid < 0 && targetPkg != null) {
             try {
                 targetUid = pm.getPackageUid(targetPkg, UserHandle.getUserId(callingUid));
                 if (targetUid < 0) {
                     if (DEBUG_URI_PERMISSION) Slog.v(TAG_URI_PERMISSION,
                             "Can't grant URI permission no uid for: " + targetPkg);
                     return -1;
                 }
             } catch (RemoteException ex) {
                 return -1;
             }
         }
 
         if (targetUid >= 0) {
             // First...  does the target actually need this permission?
             if (checkHoldingPermissionsLocked(pm, pi, grantUri, targetUid, modeFlags)) {
                 // No need to grant the target this permission.
                 if (DEBUG_URI_PERMISSION) Slog.v(TAG_URI_PERMISSION,
                         "Target " + targetPkg + " already has full permission to " + grantUri);
                 return -1;
             }
         } else {
             // First...  there is no target package, so can anyone access it?
             boolean allowed = pi.exported;
             if ((modeFlags&Intent.FLAG_GRANT_READ_URI_PERMISSION) != 0) {
                 if (pi.readPermission != null) {
                     allowed = false;
                 }
             }
             if ((modeFlags&Intent.FLAG_GRANT_WRITE_URI_PERMISSION) != 0) {
                 if (pi.writePermission != null) {
                     allowed = false;
                 }
             }
             if (allowed) {
                 return -1;
             }
         }
 
         /* There is a special cross user grant if:
          * - The target is on another user.
          * - Apps on the current user can access the uri without any uid permissions.
          * In this case, we grant a uri permission, even if the ContentProvider does not normally
          * grant uri permissions.
          */
         boolean specialCrossUserGrant = UserHandle.getUserId(targetUid) != grantUri.sourceUserId
                 && checkHoldingPermissionsInternalLocked(pm, pi, grantUri, callingUid,
                 modeFlags, false /*without considering the uid permissions*/);
 
         // Second...  is the provider allowing granting of URI permissions?
         if (!specialCrossUserGrant) {
             if (!pi.grantUriPermissions) {
                 throw new SecurityException("Provider " + pi.packageName
                         + "/" + pi.name
                         + " does not allow granting of Uri permissions (uri "
                         + grantUri + ")");
             }
             if (pi.uriPermissionPatterns != null) {
                 final int N = pi.uriPermissionPatterns.length;
                 boolean allowed = false;
                 for (int i=0; i<N; i++) {
                     if (pi.uriPermissionPatterns[i] != null
                             && pi.uriPermissionPatterns[i].match(grantUri.uri.getPath())) {
                         allowed = true;
                         break;
                     }
                 }
                 if (!allowed) {
                     throw new SecurityException("Provider " + pi.packageName
                             + "/" + pi.name
                             + " does not allow granting of permission to path of Uri "
                             + grantUri);
                 }
             }
         }
 
         // Third...  does the caller itself have permission to access
         // this uri?
         if (UserHandle.getAppId(callingUid) != Process.SYSTEM_UID) {
             if (!checkHoldingPermissionsLocked(pm, pi, grantUri, callingUid, modeFlags)) {
                 // Require they hold a strong enough Uri permission
                 if (!checkUriPermissionLocked(grantUri, callingUid, modeFlags)) {
                     throw new SecurityException("Uid " + callingUid
                             + " does not have permission to uri " + grantUri);
                 }
             }
         }
         return targetUid;
     }
}