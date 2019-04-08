package org.measure.smmmeasuremodeling.handlers.commands;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.standard.ApplyPatternStandardHandler;
import org.modelio.metamodel.mda.Project;
import org.modelio.vcore.smkernel.mapi.MObject;

@objid ("f25f926e-1eaf-463f-8b8d-705106a71a2d")
public class CreateSMMHandler extends ApplyPatternStandardHandler {
    @objid ("0a55f99c-d36a-4e25-ae63-1f78013b96a1")
    @Override
    public boolean accept(final List<MObject> selectedElements, final IModule module) {
        if (super.accept(selectedElements, module) == false) {
            return false;
        }
        
        MObject parent = selectedElements.get(0);
        if(parent.getCompositionOwner() instanceof Project){
            return true;
        }
        return false;
    }

}
