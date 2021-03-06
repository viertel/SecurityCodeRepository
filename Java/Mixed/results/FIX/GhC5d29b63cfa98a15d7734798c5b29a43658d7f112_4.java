class GhC5d29b63cfa98a15d7734798c5b29a43658d7f112_4{
@Override
     public Answer execute(final MigrateCommand command, final LibvirtComputingResource libvirtComputingResource) {
         final String vmName = command.getVmName();
 
         String result = null;
 
         List<InterfaceDef> ifaces = null;
         List<DiskDef> disks = null;
 
         Domain dm = null;
         Connect dconn = null;
         Domain destDomain = null;
         Connect conn = null;
         String xmlDesc = null;
         try {
             final LibvirtUtilitiesHelper libvirtUtilitiesHelper = libvirtComputingResource.getLibvirtUtilitiesHelper();
 
             conn = libvirtUtilitiesHelper.getConnectionByVmName(vmName);
             ifaces = libvirtComputingResource.getInterfaces(conn, vmName);
             disks = libvirtComputingResource.getDisks(conn, vmName);
             dm = conn.domainLookupByName(vmName);
             /*
                 We replace the private IP address with the address of the destination host.
                 This is because the VNC listens on the private IP address of the hypervisor,
                 but that address is ofcourse different on the target host.
 
                 MigrateCommand.getDestinationIp() returns the private IP address of the target
                 hypervisor. So it's safe to use.
 
                 The Domain.migrate method from libvirt supports passing a different XML
                description for the instance to be used on the target host.
                This is supported by libvirt-java from version 0.50.0
                CVE-2015-3252: Get XML with sensitive information suitable for migration by using
                               VIR_DOMAIN_XML_MIGRATABLE flag (value = 8)
                               https://libvirt.org/html/libvirt-libvirt-domain.html#virDomainXMLFlags
             */
            xmlDesc = dm.getXMLDesc(8).replace(libvirtComputingResource.getPrivateIp(), command.getDestinationIp());

            dconn = libvirtUtilitiesHelper.retrieveQemuConnection("qemu+tcp://" + command.getDestinationIp() + "/system");

             //run migration in thread so we can monitor it
             s_logger.info("Live migration of instance " + vmName + " initiated");
             final ExecutorService executor = Executors.newFixedThreadPool(1);
             final Callable<Domain> worker = new MigrateKVMAsync(libvirtComputingResource, dm, dconn, xmlDesc, vmName, command.getDestinationIp());
             final Future<Domain> migrateThread = executor.submit(worker);
             executor.shutdown();
             long sleeptime = 0;
             while (!executor.isTerminated()) {
                 Thread.sleep(100);
                 sleeptime += 100;
                 if (sleeptime == 1000) { // wait 1s before attempting to set downtime on migration, since I don't know of a VIR_DOMAIN_MIGRATING state
                     final int migrateDowntime = libvirtComputingResource.getMigrateDowntime();
                     if (migrateDowntime > 0 ) {
                         try {
                             final int setDowntime = dm.migrateSetMaxDowntime(migrateDowntime);
                             if (setDowntime == 0 ) {
                                 s_logger.debug("Set max downtime for migration of " + vmName + " to " + String.valueOf(migrateDowntime) + "ms");
                             }
                         } catch (final LibvirtException e) {
                             s_logger.debug("Failed to set max downtime for migration, perhaps migration completed? Error: " + e.getMessage());
                         }
                     }
                 }
                 if (sleeptime % 1000 == 0) {
                     s_logger.info("Waiting for migration of " + vmName + " to complete, waited " + sleeptime + "ms");
                 }
 
                 // pause vm if we meet the vm.migrate.pauseafter threshold and not already paused
                 final int migratePauseAfter = libvirtComputingResource.getMigratePauseAfter();
                 if (migratePauseAfter > 0 && sleeptime > migratePauseAfter && dm.getInfo().state == DomainState.VIR_DOMAIN_RUNNING ) {
                     s_logger.info("Pausing VM " + vmName + " due to property vm.migrate.pauseafter setting to " + migratePauseAfter+ "ms to complete migration");
                     try {
                         dm.suspend();
                     } catch (final LibvirtException e) {
                         // pause could be racy if it attempts to pause right when vm is finished, simply warn
                         s_logger.info("Failed to pause vm " + vmName + " : " + e.getMessage());
                     }
                 }
             }
             s_logger.info("Migration thread for " + vmName + " is done");
 
             destDomain = migrateThread.get(10, TimeUnit.SECONDS);
 
             if (destDomain != null) {
                 for (final DiskDef disk : disks) {
                     libvirtComputingResource.cleanupDisk(disk);
                 }
             }
         } catch (final LibvirtException e) {
             s_logger.debug("Can't migrate domain: " + e.getMessage());
             result = e.getMessage();
         } catch (final InterruptedException e) {
             s_logger.debug("Interrupted while migrating domain: " + e.getMessage());
             result = e.getMessage();
         } catch (final ExecutionException e) {
             s_logger.debug("Failed to execute while migrating domain: " + e.getMessage());
             result = e.getMessage();
         } catch (final TimeoutException e) {
             s_logger.debug("Timed out while migrating domain: " + e.getMessage());
             result = e.getMessage();
         } finally {
             try {
                 if (dm != null) {
                     if (dm.isPersistent() == 1) {
                         dm.undefine();
                     }
                     dm.free();
                 }
                 if (dconn != null) {
                     dconn.close();
                 }
                 if (destDomain != null) {
                     destDomain.free();
                 }
             } catch (final LibvirtException e) {
                 s_logger.trace("Ignoring libvirt error.", e);
             }
         }
 
         if (result != null) {
         } else {
             libvirtComputingResource.destroyNetworkRulesForVM(conn, vmName);
             for (final InterfaceDef iface : ifaces) {
                 // We don't know which "traffic type" is associated with
                 // each interface at this point, so inform all vif drivers
                 final List<VifDriver> allVifDrivers = libvirtComputingResource.getAllVifDrivers();
                 for (final VifDriver vifDriver : allVifDrivers) {
                     vifDriver.unplug(iface);
                 }
             }
         }
 
         return new MigrateAnswer(command, result == null, result, null);
     }
}