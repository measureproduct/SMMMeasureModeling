package org.measure.smmmeasuremodeling.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smmmeasuremodeling.api.ISMMMeasureModelingPeerModule;
import org.modelio.api.module.context.configuration.IModuleAPIConfiguration;
import org.modelio.vbasic.version.Version;

@objid ("92eca532-a6bb-4264-a589-9ca9b988943e")
public class SMMMeasureModelingPeerModule implements ISMMMeasureModelingPeerModule {
    @objid ("c4490210-4e93-4d53-b381-ccb3b5b4176d")
    private SMMMeasureModelingModule module = null;

    @objid ("ffc65bda-1514-47ed-b997-dcecf8827c2b")
    private IModuleAPIConfiguration peerConfiguration;

    @objid ("27dc8f9b-065a-4bfd-a5ad-a862b0c767c5")
    public SMMMeasureModelingPeerModule(final SMMMeasureModelingModule module, final IModuleAPIConfiguration peerConfiguration) {
        this.module = module;
        this.peerConfiguration = peerConfiguration;
    }

    @objid ("8d33c4ce-0ecc-4d30-8870-0fe1aafe7594")
    public IModuleAPIConfiguration getConfiguration() {
        return this.peerConfiguration;
    }

    @objid ("fd69fa81-5a5c-4ae1-a0f6-b030dcdcc178")
    public String getDescription() {
        return this.module.getDescription();
    }

    @objid ("435ef448-93ef-4136-9125-df7f2c99a354")
    public String getName() {
        return this.module.getName();
    }

    @objid ("7b005422-9cd1-4d0e-8a20-a3ad8495a133")
    public Version getVersion() {
        return this.module.getVersion();
    }

    @objid ("a5355770-69f6-4e21-80c6-a2534c675f0d")
    void init() {
    }

}
