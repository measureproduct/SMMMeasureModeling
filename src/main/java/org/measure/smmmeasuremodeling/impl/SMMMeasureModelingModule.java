package org.measure.smmmeasuremodeling.impl;

import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.modelio.api.module.AbstractJavaModule;
import org.modelio.api.module.context.IModuleContext;
import org.modelio.api.module.lifecycle.IModuleLifeCycleHandler;
import org.modelio.api.module.parameter.IParameterEditionModel;

@objid ("ee251190-0e4a-4163-b55f-09fa532aa9de")
public class SMMMeasureModelingModule extends AbstractJavaModule {
    @objid ("cf93dc8e-c05e-4e80-a36e-ed02c3ec3958")
    private static final String MODULE_IMAGE = "/res/icon/module.png";

    @objid ("2d4b1c7a-040a-4d53-9e9a-167855642aee")
    private SMMMeasureModelingPeerModule peerModule = null;

    @objid ("85da796c-bdd7-451a-aa0f-46b5b6bd8f9d")
    private SMMMeasureModelingLifeCycleHandler lifeCycleHandler = null;

    @objid ("42f074e6-09f9-480e-8ef6-ded4862d11d9")
    private static SMMMeasureModelingModule instance;

    @objid ("35302960-af05-4fc7-a6ad-473b94938ff3")
    public SMMMeasureModelingModule(final IModuleContext moduleContext) {
        super(moduleContext);
        
        SMMMeasureModelingModule.instance = this;
        
        this.lifeCycleHandler  = new SMMMeasureModelingLifeCycleHandler(this);
        this.peerModule = new SMMMeasureModelingPeerModule(this, moduleContext.getPeerConfiguration());
        init();
    }

    @objid ("7a44e5cb-6233-4b63-8ee0-aae84d769bc2")
    @Override
    public SMMMeasureModelingPeerModule getPeerModule() {
        return this.peerModule;
    }

    /**
     * Return the LifeCycleHandler  attached to the current module. This handler is used to manage the module lifecycle by declaring the desired implementation for the start, select... methods.
     */
    @objid ("5d6f6c3f-2d46-4648-93b7-0f0d36a88d13")
    @Override
    public IModuleLifeCycleHandler getLifeCycleHandler() {
        return this.lifeCycleHandler;
    }

    /**
     * Method automatically called just after the creation of the module. The module is automatically instanciated at the beginning
     * of the MDA lifecycle and constructor implementation is not accessible to the module developer. The <code>init</code> method
     * allows the developer to execute the desired initialization.
     */
    @objid ("ba44b4e2-9c01-4793-9822-16cc47a46de7")
    @Override
    public IParameterEditionModel getParametersEditionModel() {
        return super.getParametersEditionModel();
    }

    @objid ("4f75a327-cdae-4660-8d84-7006d063937f")
    @Override
    public String getModuleImagePath() {
        return SMMMeasureModelingModule.MODULE_IMAGE;
    }

    @objid ("a0f3b1fe-4386-4acb-9db9-32c59de40561")
    public static SMMMeasureModelingModule getInstance() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return instance;
    }

}
