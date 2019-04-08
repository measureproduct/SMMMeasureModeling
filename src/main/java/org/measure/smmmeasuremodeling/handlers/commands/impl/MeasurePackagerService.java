package org.measure.smmmeasuremodeling.handlers.commands.impl;

import java.io.File;
import java.nio.file.Path;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
import org.measure.smmmeasuremodeling.impl.SMMMeasureModelingModule;
import org.modelio.metamodel.uml.statik.Class;

@objid ("f67f3cae-2acf-4ef7-a1f9-03fce6ac76c2")
public class MeasurePackagerService {
    @objid ("d4e9ed85-f555-424a-937e-85cb265a455f")
    private Class measure;

    @objid ("b9b679d7-3898-4a09-a262-40ad02d0b22d")
    public MeasurePackagerService(Class measure) {
        this.measure = measure;
    }

    @objid ("531732a5-6fc7-4f79-a314-9ac7911cfbff")
    public boolean buildMeasure() {
        Path measurePath = new File(SMMMeasureModelingModule.getInstance().getModuleContext().getConfiguration().getParameterValue("Measure_Directory_Path")).toPath().resolve(measure.getName().replace(" ", "_"));
        
        //        Path target = measurePath.resolve("target/" + measure.getName().replace(" ", "_") + "-1.0.0.jar");
        //        if (target.toFile().exists()) {
        //            target.toFile().delete();
        //        }
        //        
        //        Runtime rt = Runtime.getRuntime();
        //        try {
        //            Path cmdPath = measurePath.resolve("mvnw.cmd");
        //            String command = cmdPath.toString() + " package";
        //            System.out.println("COMMAND : " + command);
        //            Process proc = rt.exec(command, null, measurePath.toFile());
        //        
        //            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        //        
        //            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        //        
        //            // read the output from the command
        //            StringBuffer repport = new StringBuffer();
        //            String s = null;
        //            while ((s = stdInput.readLine()) != null) {
        //                System.out.println(s);
        //                if (s != null)
        //                    repport.append(s);
        //            }
        //        
        //            // read any errors from the attempted command
        //            while ((s = stdError.readLine()) != null) {
        //                System.out.println(s);
        //            }
        //        
        //            if (!target.toFile().exists()) {
        //                DialogManager.openError("Measure Packagine Error", repport.toString());
        //                return false;
        //            } else {
        //                // Coppy Library
        //                coppyLibrary(measurePath.resolve("target/lib"), measurePath.resolve("lib"));
        //                return true;
        //            }
        //        
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        return false;
    }

}
