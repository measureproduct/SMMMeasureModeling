package org.measure.smmmeasuremodeling.handlers.commands.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smm.measure.model.FieldType;
import org.measure.smm.measure.model.MeasureReference;
import org.measure.smm.measure.model.MeasureType;
import org.measure.smm.measure.model.MeasureUnit;
import org.measure.smm.measure.model.MeasureUnitField;
import org.measure.smm.measure.model.SMMMeasure;
import org.measure.smm.measure.model.ScopeProperty;
import org.measure.smm.measure.model.ScopePropertyEnum;
import org.measure.smm.measure.model.ScopePropertyEnumValue;
import org.measure.smm.measure.model.ScopePropertyType;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.statik.AggregationKind;
import org.modelio.metamodel.uml.statik.AssociationEnd;
import org.modelio.metamodel.uml.statik.Attribute;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.Classifier;
import org.modelio.metamodel.uml.statik.Enumeration;
import org.modelio.metamodel.uml.statik.EnumerationLiteral;
import org.modelio.metamodel.uml.statik.GeneralClass;

@objid ("cfbdd424-31a7-40e6-bbde-5f5c46c99776")
public class MetaDataGenerationService {
    @objid ("0831e72c-c62b-45cf-b86b-197b6db3b774")
    private Class measure;

    @objid ("03d10e74-67b6-4c1a-91fe-7f518fecb7d1")
    public MetaDataGenerationService(Class measure) {
        this.measure = measure;
    }

    @objid ("3dd46300-b1ee-4bc0-9b06-529746094ddc")
    public void generateMetadatas(Path metadataFile) {
        SMMMeasure measureModel = calculateMetadata();
        
        if (metadataFile.toFile().exists()) {
            metadataFile.toFile().delete();
        }
        
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(SMMMeasure.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(measureModel, metadataFile.toFile());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @objid ("5fd68726-fa54-41de-85a2-267b77f1f95d")
    public SMMMeasure createDerivedMeasure(String type) {
        // Create Measure
        SMMMeasure measureModel = new SMMMeasure();
        measureModel.setName(this.measure.getName().replace(" ", ""));
        String description = this.measure.getNoteContent("ModelerModule", "description");
        if (description != null) {
            measureModel.setDescription(description);
            System.out.println(description);
        }
        
        String mtype = this.measure.getTagValue("SMMMeasureModeling", "metadata.type");
        if (mtype == null || "".equals(mtype)) {
            mtype = "Default";
        }
        measureModel.setCategory(mtype);
        
        String developer = this.measure.getTagValue("SMMMeasureModeling", "matadata.developer");
        if (developer == null || "".equals(developer)) {
            developer = "PublicUser";
        }
        measureModel.setProvider(developer);
        
        switch (type) {
        case "CollectiveMeasure":
            measureModel.setType(MeasureType.COLLECTIVE);
            break;
        
        case "BinaryMeasure":
            measureModel.setType(MeasureType.BINARY);
            break;
        
        case "RatioMeasure":
            measureModel.setType(MeasureType.RATIO);
            break;
        
        case "RescaledMeasure":
            measureModel.setType(MeasureType.RESCALED);
            break;
        
        case "GradeMeasure":
            measureModel.setType(MeasureType.GRADE);
            break;
        
        case "RankingMeasure":
            measureModel.setType(MeasureType.RACKING);
            break;
        default:
        
        }
        
        Class unit = getDependency(this.measure, "SMMMeasureModeling", "UnitLink");
        if (unit != null) {
            measureModel.setUnit(createUnit(unit));
        }
        
        measureModel.getScopeProperties().addAll(createScopeModel());
        
        for (Dependency dp : this.measure.getDependsOnDependency()) {
            if (dp.isStereotyped("SMMMeasureModeling", "BaseMeasureRelationhip")) {
                String refRole = dp.getName();
                String refType = dp.getDependsOn().getName();
                int number = 1;
        
                if (dp.isStereotyped("SMMMeasureModeling", "BaseNMeasureRelationship")) {
                    try {
                        number = Integer.valueOf(dp.getTagValue("SMMMeasureModeling", "influence"));
                    } catch (NumberFormatException e) {
                        System.out.println(e);
                    }
                }
        
                MeasureReference ref = new MeasureReference();
                ref.setMeasureRef(refType);
                ref.setRole(refRole);
                ref.setNumber(number);
                measureModel.getReferences().add(ref);
            }
        }
        return measureModel;
    }

    @objid ("4ad6a1fa-9a64-4a1f-8ef6-0995cc70ee60")
    private SMMMeasure createDirectMeasure(String type) {
        // Create Measure
        SMMMeasure measureModel = new SMMMeasure();
        measureModel.setScopeProperties(new ArrayList<>());
        measureModel.setName(this.measure.getName().replace(" ", ""));
        String description = this.measure.getNoteContent("ModelerModule", "decription");
        if (description != null) {
            measureModel.setDescription(description);
        }
        
        String mtype = this.measure.getTagValue("SMMMeasureModeling", "metadata.type");
        if (mtype == null || "".equals(mtype)) {
            mtype = "Default";
        }
        measureModel.setCategory(mtype);
        
        String developer = this.measure.getTagValue("SMMMeasureModeling", "matadata.developer");
        if (developer == null || "".equals(developer)) {
            developer = "Measure.org";
        }
        measureModel.setProvider(developer);
        
        switch (type) {
        case "DirectMeasure":
            measureModel.setType(MeasureType.DIRECT);
            break;
        case "CountingMeasure":
            measureModel.setType(MeasureType.COUNTING);
            break;
        
        default:
        
        }
        
        Class unit = getDependency(this.measure, "SMMMeasureModeling", "UnitLink");
        if (unit != null) {
            measureModel.setUnit(createUnit(unit));
        }
        
        measureModel.getScopeProperties().addAll(createScopeModel());
        return measureModel;
    }

    @objid ("8d91d7df-6bd1-442d-8345-18002a9ab88f")
    private List<ScopeProperty> createScopeModel() {
        List<ScopeProperty> result = new ArrayList<ScopeProperty>();
        
        Class scope = getDependency(this.measure, "SMMMeasureModeling", "ScopeLink");
        if (scope != null) {
            for (Attribute property : scope.getOwnedAttribute()) {
                ScopeProperty scopeProp = new ScopeProperty();
                scopeProp.setName(property.getName());
                scopeProp.setDefaultValue(property.getValue());
                scopeProp.setType(findScopeType(property));
                
                if(property.getType() instanceof Enumeration){
                    ScopePropertyEnum scopeEnum = new ScopePropertyEnum();
                    for(EnumerationLiteral val : ((Enumeration)property.getType()).getValue()){
                        ScopePropertyEnumValue value = new ScopePropertyEnumValue();
                        value.setLabel(val.getName());
                        value.setValue(val.getName());
                        scopeEnum.getEnumvalue().add(value);
                    }
                    scopeProp.setEnumType(scopeEnum);
                }
        
                String description = this.measure.getNoteContent("ModelerModule", "decription");
                if (description != null) {
                    scopeProp.setDescription(description);
                }
                result.add(scopeProp);
            }
        }
        return result;
    }

    @objid ("facd01c5-6d5e-4ddb-97fc-2cad7bec8cf3")
    private Class getDependency(Class source, String module, String stereotype) {
        for (Dependency dp : source.getDependsOnDependency()) {
            if (dp.isStereotyped(module, stereotype)) {
                return (Class) dp.getDependsOn();
            }
        }
        return null;
    }

    @objid ("4530637d-73f9-467a-9afa-03aebc591d6f")
    public SMMMeasure calculateMetadata() {
        String type = measure.getExtension().get(0).getName();
        switch (type) {
        case "DirectMeasure":
        case "CountingMeasure":
            return createDirectMeasure(type);
        default:
            return createDerivedMeasure(type);
        }
    }

    @objid ("1f4d49ee-0874-4f03-ac36-98342fc7dc00")
    private FieldType findFinedType(Attribute attr) {
        if (attr.getType() != null) {
            String mftype = attr.getType().getName();
            if ("string".equals(mftype)) {
                return FieldType.u_text;
            } else if ("boolean".equals(mftype)) {
                return FieldType.u_boolean;
            } else if ("char".equals(mftype)) {
                return FieldType.u_text;
            } else if ("date".equals(mftype)) {
                return FieldType.u_date;
            } else if ("bouble".equals(mftype)) {
                return FieldType.u_double;
            } else if ("float".equals(mftype)) {
                return FieldType.u_float;
            } else if ("integer".equals(mftype)) {
                return FieldType.u_integer;
            } else if ("long".equals(mftype)) {
                return FieldType.u_long;
            } else if ("short".equals(mftype)) {
                return FieldType.u_short;
            }
        
            for (FieldType ftype : FieldType.values()) {
                if (attr.getType().getName().equals(ftype.name())) {
                    return ftype;
                }
            }
        }
        return FieldType.u_text;
    }

    @objid ("e75e5fee-f40c-41a9-aa5c-3a653129686f")
    private MeasureUnit createUnit(Class unit) {
        MeasureUnit nunit = new MeasureUnit();
        nunit.setName(unit.getName());
        for (Attribute attr : unit.getOwnedAttribute()) {
            MeasureUnitField field = new MeasureUnitField();
            field.setFieldName(attr.getName());
            field.setFieldType(findFinedType(attr));
            nunit.getFields().add(field);
        }
        for (AssociationEnd end : unit.getOwnedEnd()) {
            if (end.getAggregation().equals(AggregationKind.KINDISCOMPOSITION)) {
                Classifier target = end.getOppositeOwner().getOwner();
                if (target instanceof Class) {
                    MeasureUnitField field = new MeasureUnitField();
                    field.setFieldName(target.getName());
                    field.setSubtype(createUnit((Class) target));
                    nunit.getFields().add(field);
                }
            }
        }
        return nunit;
    }

    @objid ("53e79b46-c48d-4fea-9c1f-3e551a870848")
    private ScopePropertyType findScopeType(Attribute property) {
        if(property.getType() != null){            
            GeneralClass sptype = property.getType();
            if(sptype instanceof Enumeration){
                return ScopePropertyType.ENUM;
            }
            for(ScopePropertyType type : ScopePropertyType.values()){
                if(sptype.getName().equals(type.name())){
                    return type;
                }
            }    
        
            if ("string".equals(sptype)) {
                return ScopePropertyType.STRING;
            } else if ("date".equals(sptype)) {
                return ScopePropertyType.DATE;
            } else if ("integer".equals(sptype)) {
                return ScopePropertyType.INTEGER;
            } else if ("float".equals(sptype)) {
                return ScopePropertyType.FLOAT;
            }     
        }
        return ScopePropertyType.STRING;
    }

}
