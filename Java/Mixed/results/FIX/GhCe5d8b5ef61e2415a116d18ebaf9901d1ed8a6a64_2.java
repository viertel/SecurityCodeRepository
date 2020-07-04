class GhCe5d8b5ef61e2415a116d18ebaf9901d1ed8a6a64_2{
public BeanProperties(Class<?> type) throws ELException {
             this.type = type;
             this.properties = new HashMap<>();
            try {
                BeanInfo info = Introspector.getBeanInfo(this.type);
                PropertyDescriptor[] pds = info.getPropertyDescriptors();
                for (PropertyDescriptor pd: pds) {
                    this.properties.put(pd.getName(), new BeanProperty(type, pd));
                }
                if (System.getSecurityManager() != null) {
                    // When running with SecurityManager, some classes may be
                    // not accessible, but have accessible interfaces.
                    populateFromInterfaces(type);
                }
            } catch (IntrospectionException ie) {
                throw new ELException(ie);
            }
        }
private void populateFromInterfaces(Class<?> aClass) throws IntrospectionException {
            Class<?> interfaces[] = aClass.getInterfaces();
            if (interfaces.length > 0) {
                for (Class<?> ifs : interfaces) {
                    BeanInfo info = Introspector.getBeanInfo(ifs);
                    PropertyDescriptor[] pds = info.getPropertyDescriptors();
                    for (PropertyDescriptor pd : pds) {
                        if (!this.properties.containsKey(pd.getName())) {
                            this.properties.put(pd.getName(), new BeanProperty(
                                    this.type, pd));
                        }
                    }
                }
            }
            Class<?> superclass = aClass.getSuperclass();
            if (superclass != null) {
                populateFromInterfaces(superclass);
            }
        }
}