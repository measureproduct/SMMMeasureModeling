package org.measure.smmmeasuremodeling.handlers.commands.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smmmeasuremodeling.impl.SMMMeasureModelingModule;
import org.modelio.metamodel.uml.statik.Class;

@objid ("b5df56bb-4da4-48ab-8edc-905756a98c5d")
public class MeasureGeneratoionService {
    @objid ("3e8ecf1c-5393-492f-9c59-20d686f86580")
    private Class measure;

    @objid ("a6fe4779-008d-4780-964a-a6785181d521")
    public MeasureGeneratoionService(Class measure) {
        this.measure = measure;
    }

    @objid ("9e65c27d-8400-4b5f-bb31-994125028033")
    public void generateMeasure() {
        // Calculate Measure Path
        Path resourcePath = SMMMeasureModelingModule.getInstance().getModuleContext().getConfiguration().getModuleResourcesPath();
        Path measurePath = new File(SMMMeasureModelingModule.getInstance().getModuleContext().getConfiguration().getParameterValue("Measure_Directory_Path")).toPath();

        // Create Directory Structure
        Path measureDir = null;
        Path javaDir = null;
        Path testDir = null;
        Path dataDir = null;
        try {
            measureDir = Files.createDirectories(measurePath);
            javaDir = Files.createDirectories(measureDir.resolve("src/main/java"));
            testDir = Files.createDirectories(measureDir.resolve("src/test/java"));
            dataDir = Files.createDirectories(measureDir.resolve("metadata"));
            Files.createDirectories(measureDir.resolve("lib"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Coppy Maven Packaguing Utils
        try {
            Files.copy(resourcePath.resolve("res/tool/assembly.xml"), measureDir.resolve("assembly.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate POM
        geteratePOM(measure, measureDir);

        // Generate metadata file
        MetaDataGenerationService generator = new MetaDataGenerationService(measure);
        generator.generateMetadatas(dataDir.resolve("MeasureMetadata.xml"));

        // Generate Java
        JavaGenerationService proxy = new JavaGenerationService();
        proxy.generateJavaCode(measure, javaDir, testDir);
    }

    @objid ("ff677469-f4d0-4c0e-8b9c-76073e5ce80c")
    private void geteratePOM(Class measure, Path measureDir) {
        Path file = measureDir.resolve("pom.xml");
        try {
            if (file.toFile().exists()) {
                file.toFile().delete();
            }
            Files.createFile(file);

            try (BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
                writer.write("        xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n");
                writer.write("        <modelVersion>4.0.0</modelVersion>\n");
                writer.write("        <groupId>org.measure.smmmeasure.impl</groupId>\n");
                writer.write("        <artifactId>" + measure.getName().replace(" ", "") + "</artifactId>\n");
                writer.write("        <version>1.0.0</version>\n");
                writer.write("\n");
                writer.write("        <repositories>\n");
                writer.write("            <repository>\n");
                writer.write("                <id>Modelio</id>\n");
                writer.write("                <url>http://repository.modelio.org/</url>\n");
                writer.write("            </repository>\n");

                for (Class sub : measure.getOwnedElement(Class.class)) {
                    if (sub.isStereotyped("SMMMeasureModeling", "SmmImplementation")) {
                        String extention = sub.getNoteContent("SMMMeasureModeling", "repository");
                        if (extention != null && !"".equals(extention)) {
                            writer.write("\n");
                            writer.write(extention + "\n");
                            writer.write("\n");
                        }
                    }
                }
                writer.write("        </repositories>\n");
                writer.write("\n");
                writer.write("        <dependencies>\n");
                writer.write("            <dependency>\n");
                writer.write("                <groupId>org.modelio</groupId>\n");
                writer.write("                <artifactId>javadesigner</artifactId>\n");
                writer.write("                <version>2.2.0</version>\n");
                writer.write("            </dependency>\n");
                writer.write("\n");
                writer.write("            <dependency>\n");
                writer.write("                <groupId>org.measure.smmmeasure.api</groupId>\n");
                writer.write("                <artifactId>SMMMeasureApi</artifactId>\n");
                writer.write("                <version>0.7.00</version>\n");
                writer.write("            </dependency>\n");
                writer.write("\n");

                for (Class sub : measure.getOwnedElement(Class.class)) {
                    if (sub.isStereotyped("SMMMeasureModeling", "SmmImplementation")) {
                        String extention = sub.getNoteContent("SMMMeasureModeling", "dependency");
                        if (extention != null && !"".equals(extention)) {
                            writer.write("\n");
                            writer.write(extention + "\n");
                            writer.write("\n");
                        }
                    }
                }

                writer.write("<dependency>\n");
                writer.write("<groupId>junit</groupId>\n");
                writer.write("<artifactId>junit</artifactId>\n");
                writer.write("<version>4.11</version>\n");
                writer.write("</dependency>\n");
                writer.write("</dependencies>\n");
                writer.write("<build>\n");
                writer.write("<plugins>\n");
                writer.write(" <plugin>\n");
                writer.write("<groupId>org.apache.maven.plugins</groupId>\n");
                writer.write("<artifactId>maven-compiler-plugin</artifactId>\n");
                writer.write("<configuration>\n");
                writer.write("<source>1.8</source>\n");
                writer.write("<target>1.8</target>\n");
                writer.write("<encoding>UTF-8</encoding>\n");
                writer.write("</configuration>\n");
                writer.write("</plugin>\n");
                writer.write("<plugin>\n");
                writer.write("<artifactId>maven-assembly-plugin</artifactId>\n");
                writer.write("<configuration>\n");
                writer.write("<descriptors>\n");
                writer.write("<descriptor>assembly.xml</descriptor>\n");
                writer.write("</descriptors>\n");
                writer.write("<finalName>${project.artifactId}</finalName>\n");
                writer.write("</configuration>\n");
                writer.write("<executions>\n");
                writer.write("<execution>\n");
                writer.write("<id>make-assembly</id>\n");
                writer.write("<phase>package</phase>\n");
                writer.write("<goals>\n");
                writer.write("<goal>single</goal>\n");
                writer.write("</goals>\n");
                writer.write("</execution>\n");
                writer.write("</executions>\n");
                writer.write("</plugin>\n");
                writer.write("</plugins>\n");
                writer.write("</build>\n");
                writer.write("</project>\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
