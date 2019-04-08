package org.measure.smmmeasuremodeling.handlers.tools;

import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.modelio.api.modelio.Modelio;
import org.modelio.api.modelio.diagram.IDiagramGraphic;
import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.api.modelio.diagram.IDiagramLink.LinkRouterKind;
import org.modelio.api.modelio.diagram.IDiagramLink;
import org.modelio.api.modelio.diagram.ILinkPath;
import org.modelio.api.modelio.diagram.tools.DefaultLinkTool;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.statik.Class;

@objid ("ad1f3b7b-de21-42f8-aad6-092fcbd35c44")
public class RescaleLinkHandler extends DefaultLinkTool {
    @objid ("118f56fb-609a-4804-bab0-15cf8869d344")
    @Override
    public boolean acceptFirstElement(final IDiagramHandle diagramHandle, final IDiagramGraphic targetNode) {
        return (targetNode.getElement() instanceof Class) && ((Class) targetNode.getElement()).isStereotyped("SMMMeasureModeling", "RescaledMeasure");
    }

    @objid ("80ce3b9d-1c80-4c50-a56b-089374d5de20")
    @Override
    public boolean acceptSecondElement(final IDiagramHandle diagramHandle, final IDiagramGraphic originNode, final IDiagramGraphic targetNode) {
        return (targetNode.getElement() instanceof Dependency);
    }

    @objid ("84d83aa6-33d3-474f-9afe-82050ab8fc2a")
    @Override
    public void actionPerformed(final IDiagramHandle diagramHandle, final IDiagramGraphic originNode, final IDiagramGraphic targetNode, final LinkRouterKind routerKind, final ILinkPath path) {
        try (ITransaction tr = Modelio.getInstance().getModelingSession().createTransaction("RescaleLinkHandler")) {
            Dependency newDep = Modelio.getInstance().getModelingSession().getModel().createDependency((ModelElement) originNode.getElement(), (ModelElement) targetNode.getElement(), "SMMMeasureModeling", "Rescales");
        
            List<IDiagramGraphic> graphs = diagramHandle.unmask(newDep, 0, 0);
        
            IDiagramLink gLink = (IDiagramLink) graphs.get(0);
            gLink.setRouterKind(routerKind);
        
            tr.commit();
        } catch (ExtensionNotFoundException e) {
            e.printStackTrace();
        }
    }

}
