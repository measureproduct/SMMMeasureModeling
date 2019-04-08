package org.measure.smmmeasuremodeling.handlers.commands.impl;

import java.nio.file.Path;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smmmeasuremodeling.impl.SMMMeasureModelingModule;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Component;
import org.modelio.module.javadesigner.impl.JavaDesignerPeerModule;

@objid ("0ebddea8-173b-429b-8529-0e218fa98f10")
public class JavaGenerationService {
    @objid ("b4088330-41ad-4e24-91f9-d756456502c5")
    private JavaDesignerPeerModule javaPeer;

    @objid ("9968c35f-7ebb-43d0-98e9-6e654a12613a")
    public JavaGenerationService() {
        this.javaPeer = SMMMeasureModelingModule.getInstance().getModuleContext().getModelioServices().getModuleService().getPeerModule(JavaDesignerPeerModule.class);
    }

    @objid ("1a38b0ba-203d-44ee-95fc-3641239ae318")
    public boolean generateJavaCode(Class measure, Path javaDir, Path testDir) {
        if (javaPeer != null) {
            for (Class sub : measure.getOwnedElement(Class.class)) {
                if (sub.isStereotyped("SMMMeasureModeling", "SmmImplementation")) {
                    for (Component javaComponent : sub.getOwnedElement(Component.class)) {
        
                        try(ITransaction tr = SMMMeasureModelingModule.getInstance().getModuleContext().getModelingSession().createTransaction("JavaGeneration")) {
                            if (javaComponent.getName().endsWith("Test")) {
                                javaComponent.putTagValue("JavaDesigner", "GenerationPath", testDir.toString());
                            } else {
                                javaComponent.putTagValue("JavaDesigner", "GenerationPath", javaDir.toString());
                            }
                            tr.commit();
                        } catch (ExtensionNotFoundException e) {
                            e.printStackTrace();
                        }
         
                        javaPeer.generate(javaComponent,false);
                    }
                }
            }
        
            return true;
        }
        return false;
    }

    @objid ("77ebe0ab-67dc-41a5-91f2-f42f5537fb31")
    public boolean reversJavaCode(Class measure) {
        if (javaPeer != null) {
        
            return true;
        }
        return false;
    }

}
