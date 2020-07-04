class GhC5d29b63cfa98a15d7734798c5b29a43658d7f112_2{
public String rebootVM(final Connect conn, final String vmName) {
         Domain dm = null;
        String msg = null;
        try {
            dm = conn.domainLookupByName(vmName);
            // Get XML Dump including the secure information such as VNC password
            // By passing 1, or VIR_DOMAIN_XML_SECURE flag
            // https://libvirt.org/html/libvirt-libvirt-domain.html#virDomainXMLFlags
            String vmDef = dm.getXMLDesc(1);
            final LibvirtDomainXMLParser parser = new LibvirtDomainXMLParser();
            parser.parseDomainXML(vmDef);
            for (final InterfaceDef nic : parser.getInterfaces()) {
                 if (nic.getNetType() == GuestNetType.BRIDGE && nic.getBrName().startsWith("cloudVirBr")) {
                     try {
                         final int vnetId = Integer.parseInt(nic.getBrName().replaceFirst("cloudVirBr", ""));
                         final String pifName = getPif(_guestBridgeName);
                         final String newBrName = "br" + pifName + "-" + vnetId;
                         vmDef = vmDef.replaceAll("'" + nic.getBrName() + "'", "'" + newBrName + "'");
                         s_logger.debug("VM bridge name is changed from " + nic.getBrName() + " to " + newBrName);
                     } catch (final NumberFormatException e) {
                         continue;
                     }
                 }
             }
             s_logger.debug(vmDef);
             msg = stopVM(conn, vmName);
             msg = startVM(conn, vmName, vmDef);
             return null;
         } catch (final LibvirtException e) {
             s_logger.warn("Failed to create vm", e);
             msg = e.getMessage();
         } catch (final InternalErrorException e) {
             s_logger.warn("Failed to create vm", e);
             msg = e.getMessage();
         } finally {
             try {
                 if (dm != null) {
                     dm.free();
                 }
             } catch (final LibvirtException e) {
                 s_logger.trace("Ignoring libvirt error.", e);
             }
         }
 
         return msg;
     }
}