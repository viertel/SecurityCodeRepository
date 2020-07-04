class GhC158497d68a92ab1e1f864a77371ea1de5c4dc5bb_2{
public String[] createApiKeyAndSecretKey(RegisterCmd cmd) {
        Account caller = CallContext.current().getCallingAccount();
        final Long userId = cmd.getId();

        User user = getUserIncludingRemoved(userId);
        if (user == null) {
            throw new InvalidParameterValueException("unable to find user by id");
        }

        Account account = _accountDao.findById(user.getAccountId());
        checkAccess(caller, null, true, account);

        // don't allow updating system user
        if (user.getId() == User.UID_SYSTEM) {
            throw new PermissionDeniedException("user id : " + user.getId() + " is system account, update is not allowed");
         }
         // don't allow baremetal system user
         if (BaremetalUtils.BAREMETAL_SYSTEM_ACCOUNT_NAME.equals(user.getUsername())) {
             throw new PermissionDeniedException("user id : " + user.getId() + " is system account, update is not allowed");
         }
 
         // generate both an api key and a secret key, update the user table with the keys, return the keys to the user
         final String[] keys = new String[2];
         Transaction.execute(new TransactionCallbackNoReturn() {
             @Override
             public void doInTransactionWithoutResult(TransactionStatus status) {
         keys[0] = createUserApiKey(userId);
         keys[1] = createUserSecretKey(userId);
             }
         });
 
         return keys;
     }
}