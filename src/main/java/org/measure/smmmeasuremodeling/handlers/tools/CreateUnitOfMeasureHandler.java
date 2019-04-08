package org.measure.smmmeasuremodeling.handlers.tools;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.eclipse.draw2d.geometry.Rectangle;
import org.modelio.api.modelio.Modelio;
import org.modelio.api.modelio.diagram.IDiagramGraphic;
import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.api.modelio.diagram.IDiagramNode;
import org.modelio.api.modelio.diagram.tools.DefaultBoxTool;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.metamodel.diagrams.StaticDiagram;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.infrastructure.ModelTree;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.NameSpace;
import org.modelio.metamodel.uml.statik.Package;

@objid ("c293a1a1-234e-4228-83ad-627b4f8e6506")
public class CreateUnitOfMeasureHandler extends DefaultBoxTool {
    @objid ("1be8f01e-02fa-4f58-95dd-0d5ad3411b33")
    public boolean acceptElement(final IDiagramHandle diagramHandle, final IDiagramGraphic targetNode) {
        if (targetNode.getElement() instanceof StaticDiagram) {
            if (targetNode.getElement().getCompositionOwner() instanceof ModelElement && ((ModelElement) targetNode.getElement().getCompositionOwner()).isStereotyped("SMMMeasureModeling", "Measure"))
                return true;
        
            if (targetNode.getElement().getCompositionOwner() instanceof ModelElement && ((ModelElement) targetNode.getElement().getCompositionOwner()).isStereotyped("SMMMeasureModeling", "MeasureCategory"))
                return true;
        } else {
            if (targetNode.getElement().getCompositionOwner() instanceof ModelElement && ((ModelElement) targetNode.getElement().getCompositionOwner()).isStereotyped("SMMMeasureModeling", "MeasureCategory"))
                return true;
        }
        return false;
    }

    @objid ("3bd518b8-a1c7-477c-a3dd-0916d469d3dc")
    @Override
    public void actionPerformed(final IDiagramHandle diagramHandle, final IDiagramGraphic parent, final Rectangle rect) {
        try (ITransaction tr = Modelio.getInstance().getModelingSession().createTransaction("CreateCharacteristicHandler")) {
        
            try {
                NameSpace owner = null;
                if (parent.getElement() instanceof StaticDiagram) {
                    Package category = null;
                    ModelElement ow1 = ((StaticDiagram) parent.getElement()).getOrigin();
                    if (ow1.isStereotyped("SMMMeasureModeling", "Measure")) {
                        category = (Package) ow1.getCompositionOwner();
                    } else if (ow1.isStereotyped("SMMMeasureModeling", "MeasureCategory")) {
                        category = (Package) ow1;
                    }
        
                    for (ModelTree sub : category.getOwnedElement()) {
                        if (sub.isStereotyped("SMMMeasureModeling", "MeasureDefinitionContainer")) {
                            owner = (Package) sub;
                            break;
                        }
                    }
        
                    if (owner == null && category != null) {
                        owner = Modelio.getInstance().getModelingSession().getModel().createPackage("Common", category, "SMMMeasureModeling", "MeasureDefinitionContainer");
                    }
                }
        
                if (owner != null) {
                    Class element = Modelio.getInstance().getModelingSession().getModel().createClass("UnitOfMeasure", owner, "SMMMeasureModeling", "UnitOfMeasure");
                    List<IDiagramGraphic> graph = diagramHandle.unmask(element, rect.x, rect.y);
                    ((IDiagramNode) graph.get(0)).setBounds(rect);
                }
            } catch (ExtensionNotFoundException e) {
                tr.rollback();
                e.printStackTrace();
                return;
            }
            tr.commit();
        }
    }

}
