package co.edu.eafit.code.binder.resolver.processors.macros;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;

public class MatrixMacro extends Macro {

    @Override
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();

        String datatype = parameters[0];
        String label = parameters[1];

        if(parameters.length>3) {
            String rowSize = parameters[2];
            String colSize = parameters[3];
            String values = parameters[4];
            sketch.addInstruction(codeBuffer -> {

                codeBuffer.appendLine(datatype + " " + label + "[" + rowSize + "]"
                        + " [ " + colSize + "]" + " = " + values + ";");
                codeBuffer.appendBreakline();
                return false;

            });
        }else{
            String values = parameters[2];
            sketch.addInstruction(codeBuffer -> {
                codeBuffer.appendLine(datatype + " " + label + "[][]" + " = " + values + ";");
                codeBuffer.appendBreakline();
                return false;
            });
        }

    }

    @Override
    public String getValue() {
        return null;
    }

}