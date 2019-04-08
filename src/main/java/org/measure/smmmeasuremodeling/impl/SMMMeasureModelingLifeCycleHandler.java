package org.measure.smmmeasuremodeling.impl;

import java.util.Map;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.modelio.api.module.lifecycle.DefaultModuleLifeCycleHandler;
import org.modelio.api.module.lifecycle.ModuleException;
import org.modelio.vbasic.version.Version;

@objid ("49cc6fe6-4433-4e5b-8436-e7777032a136")
public class SMMMeasureModelingLifeCycleHandler extends DefaultModuleLifeCycleHandler {
    @objid ("b80efb31-1dde-4e14-af31-7f839aeeb6ef")
    public SMMMeasureModelingLifeCycleHandler(final SMMMeasureModelingModule module) {
        super(module);
    }

    @objid ("d025fcda-0ed2-4243-88d2-d56e6659e31a")
    @Override
    public boolean start() throws ModuleException {
        return super.start();
    }

    @objid ("055c5016-3efd-44c7-b63c-545d6e0239fc")
    @Override
    public void stop() throws ModuleException {
        super.stop();
    }

    /**
     * @param mdaPath @return
     */
    @objid ("1622edb3-5d95-4cda-bc23-98a85e0ed25f")
    public static boolean install(final String modelioPath, final String mdaPath) throws ModuleException {
        return DefaultModuleLifeCycleHandler.install(modelioPath, mdaPath);
    }

    @objid ("471f53e8-36a1-4ff7-b34d-2618a10b6dec")
    @Override
    public boolean select() throws ModuleException {
        return super.select();
    }

    @objid ("4c96e868-1ad9-4f18-bc03-c3538cc26a8d")
    @Override
    public void upgrade(final Version oldVersion, final Map<String, String> oldParameters) throws ModuleException {
        super.upgrade(oldVersion, oldParameters);
    }

    @objid ("77266b88-f05b-440d-ac89-65aa43293437")
    @Override
    public void unselect() throws ModuleException {
        super.unselect();
    }

}
