class GhC31887f249a2471e6bbc7bf5901e0d466179368f4_2{
            // used to check the sim state transition from non-loaded to loaded
            private boolean mSimNotLoadedSeen = false;
            @Override
             public void onReceive(Context context, Intent intent) {
                 if (DBG) {
                     Log.d(TAG, "simchange mGenerationNumber=" + mGenerationNumber +
                             ", current generationNumber=" + mSimBcastGenerationNumber.get());
                 }
                 if (mGenerationNumber != mSimBcastGenerationNumber.get()) return;
 
                final String state =
                        intent.getStringExtra(IccCardConstants.INTENT_KEY_ICC_STATE);

                Log.d(TAG, "got Sim changed to state " + state + ", mSimNotLoadedSeen=" +
                        mSimNotLoadedSeen);
                if (!mSimNotLoadedSeen && !IccCardConstants.INTENT_VALUE_ICC_LOADED.equals(state)) {
                    mSimNotLoadedSeen = true;
                }
                if (mSimNotLoadedSeen && IccCardConstants.INTENT_VALUE_ICC_LOADED.equals(state)) {
                    mSimNotLoadedSeen = false;
                    try {
                        if (mContext.getResources().getString(com.android.internal.R.string.
                                config_mobile_hotspot_provision_app_no_ui).isEmpty() == false) {
                             final String tetherService = mContext.getResources().getString(
                                     com.android.internal.R.string.config_wifi_tether_enable);
                             ArrayList<Integer> tethered = new ArrayList<Integer>();
                             synchronized (mPublicSync) {
                                 Set ifaces = mIfaces.keySet();
                                 for (Object iface : ifaces) {
                                     TetherInterfaceSM sm = mIfaces.get(iface);
                                     if (sm != null && sm.isTethered()) {
                                         if (isUsb((String)iface)) {
                                             tethered.add(new Integer(USB_TETHERING));
                                         } else if (isWifi((String)iface)) {
                                             tethered.add(new Integer(WIFI_TETHERING));
                                         } else if (isBluetooth((String)iface)) {
                                             tethered.add(new Integer(BLUETOOTH_TETHERING));
                                         }
                                     }
                                 }
                             }
                             for (int tetherType : tethered) {
                                 Intent startProvIntent = new Intent();
                                 startProvIntent.putExtra(EXTRA_ADD_TETHER_TYPE, tetherType);
                                 startProvIntent.putExtra(EXTRA_RUN_PROVISION, true);
                                 startProvIntent.setComponent(
                                         ComponentName.unflattenFromString(tetherService));
                                 mContext.startServiceAsUser(startProvIntent, UserHandle.CURRENT);
                             }
                             Log.d(TAG, "re-evaluate provisioning");
                         } else {
                             Log.d(TAG, "no prov-check needed for new SIM");
                         }
                     } catch (Resources.NotFoundException e) {
                         Log.d(TAG, "no prov-check needed for new SIM");
                         // not defined, do nothing
                     }
                 }
             }
}