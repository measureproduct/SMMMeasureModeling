package org.measure.smmmeasuremodeling.handlers.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smmmeasuremodeling.handlers.commands.impl.MeasureGeneratoionService;
import org.modelio.api.modelio.Modelio;
import org.modelio.api.module.IModule;
import org.modelio.api.module.command.DefaultModuleCommandHandler;
import org.modelio.metamodel.uml.infrastructure.ModelTree;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Package;
import org.modelio.vcore.smkernel.mapi.MObject;

@objid ("44ccd90e-781c-42a1-b988-0ac49ef5cd6b")
public class GenerateMeasure extends DefaultModuleCommandHandler {
    @objid ("b1cfb238-0548-4d20-996b-fc9289718224")
    @Override
    public void actionPerformed(final List<MObject> selectedElements, final IModule module) {
        if(selectedElements.get(0) instanceof Class){
               generateMeasure((Class)selectedElements.get(0));
        }else if(selectedElements.get(0) instanceof Package){
            List<Class> measures = findMeasures((Package)selectedElements.get(0));        
            for(Class measure : measures){
                 generateMeasure((Class)measure);
            }
        }
    }

    @objid ("bb0c11a4-d804-4970-8bb7-49597329b88f")
    @Override
    public boolean accept(final List<MObject> selectedElements, final IModule module) {
        // Generated call to the super method will check the scope conditions
        // defined in Studio.
        // Do not remove this call unless you need to take full control on the
        // checks to be carried out.
        // However you can safely extends the checked conditions by adding
        // custom code.
        if (super.accept(selectedElements, module) == false) {
            return false;
        }
        return true;
    }

    @objid ("d7d6de8f-a993-4ee3-bef3-2e04d85591e7")
    private List<Class> findMeasures(Package contaner) {
        List<Class> result = new ArrayList<>();
        for(ModelTree sub : contaner.getOwnedElement()){
           if(sub instanceof Package){
               result.addAll(findMeasures((Package)sub));
           }else if(sub instanceof Class && sub.isStereotyped("SMMMeasureModeling", "Measure")){
               result.add((Class)sub);
           }
        }
        return result;
    }

    @objid ("19e2e92e-2a33-4a23-9727-0af845e4ea9f")
    private void generateMeasure(Class measure) {
        Path measurePath = Modelio.getInstance().getContext().getProjectSpacePath().toPath().resolve("Measures").resolve(measure.getName().replace(" ", ""));
        MeasureGeneratoionService generator = new MeasureGeneratoionService(measure);
        generator.generateMeasure();
    }

}
