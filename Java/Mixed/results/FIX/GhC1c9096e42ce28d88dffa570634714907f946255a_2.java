class GhC1c9096e42ce28d88dffa570634714907f946255a_2{
 private boolean isDeviceLocked() {
        boolean isLocked = false;
        final long token = Binder.clearCallingIdentity();
        try {
            final KeyguardManager keyguardManager = (KeyguardManager) mContext.getSystemService(
                    Context.KEYGUARD_SERVICE);
            boolean inKeyguardRestrictedInputMode = keyguardManager.inKeyguardRestrictedInputMode();
            if (inKeyguardRestrictedInputMode) {
               isLocked = true;
            } else {
               PowerManager powerManager = (PowerManager)mContext.getSystemService(
                 Context.POWER_SERVICE);
               isLocked = !powerManager.isScreenOn();
            }
            return isLocked;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }
}