package org.measure.smmmeasuremodeling.handlers.tools;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.eclipse.draw2d.geometry.Rectangle;
import org.measure.smmmeasuremodeling.impl.SMMMeasureModelingModule;
import org.modelio.api.modelio.Modelio;
import org.modelio.api.modelio.diagram.IDiagramGraphic;
import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.api.modelio.diagram.tools.standard.GenericBoxTool;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.metamodel.diagrams.StaticDiagram;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Package;

@objid ("d1d673b5-bd94-4c39-aa82-d89473e8927b")
public class CreateMeasureHandler extends GenericBoxTool {
    @objid ("f431853c-f535-4181-87ac-4668fb2dcced")
    @Override
    public void actionPerformed(final IDiagramHandle diagramHandle, final IDiagramGraphic parent, final Rectangle rect) {
        super.actionPerformed(diagramHandle, parent, rect);
        Package parentPackage = (Package) ((StaticDiagram)parent.getElement()).getOrigin();
        Class createdMerasure = (Class) parentPackage.getOwnedElement().get(parentPackage.getOwnedElement().size() - 1);
        try (ITransaction tr = Modelio.getInstance().getModelingSession().createTransaction("CreateMeasureHandler")){
            createdMerasure.putNoteContent("ModelerModule", "description", "TODO : Please fill the measure description");
            createdMerasure.putNoteContent("ModelerModule", "comment", "TODO : Please fill the measure description");
            createdMerasure.addStereotype("SMMMeasureModeling", "metadata");
            createdMerasure.getNote("ModelerModule", "description").setMimeType("RTF");
            StaticDiagram newDiagram = Modelio.getInstance().getModelingSession().getModel().createStaticDiagram("Measure diagram", createdMerasure, "SMMMeasureModeling", "Measure_Diagram");
            try (IDiagramHandle newDiagramHandle = Modelio.getInstance().getDiagramService().getDiagramHandle(newDiagram)) {
                newDiagramHandle.getDiagramNode().setStyle(Modelio.getInstance().getDiagramService().getStyle("Measurement diagram style"));
                newDiagramHandle.unmask(createdMerasure, 50, 100);
                newDiagramHandle.save();
            }
            
            if(createdMerasure.isStereotyped("SMMMeasureModeling", "DirectMeasure") || createdMerasure.isStereotyped("SMMMeasureModeling", "CountingMeasure")){
                 
                Map<String,Object> values = new HashMap<>();
                values.put("DirectMeasureImpl", createdMerasure);
                try {
                    Path pattern =  SMMMeasureModelingModule.getInstance().getModuleContext().getConfiguration().getModuleResourcesPath().resolve("res/generated/patterns/DirectMeasureImpl.umlt");
                    SMMMeasureModelingModule.getInstance().getModuleContext().getModelioServices().getPatternService().applyPattern(pattern, values);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Map<String,Object> values = new HashMap<>();
                values.put("DerivedMeasureImpl", createdMerasure);
                try {
                    Path pattern =  SMMMeasureModelingModule.getInstance().getModuleContext().getConfiguration().getModuleResourcesPath().resolve("res/generated/patterns/DerivedMeasureImpl.umlt");
                    SMMMeasureModelingModule.getInstance().getModuleContext().getModelioServices().getPatternService().applyPattern(pattern, values);
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        
            tr.commit();
        
        } catch (ExtensionNotFoundException e) {
            e.printStackTrace();
        }
    }

}
