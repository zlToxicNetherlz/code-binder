package com.variamos.moduino.binder.resolver.processors.macros;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.processors.data.Macro;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

public class ArrayMacro extends Macro {

    @Override
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();

        String datatype = parameters[0];
        String label = parameters[1];
        if(parameters.length>3) {
            String size = parameters[2];
            String values = parameters[3];
            sketch.addInstruction(codeBuffer -> {

                codeBuffer.appendLine(datatype + " " + label + "[" + size + "]" + " = " + values + ";");
                codeBuffer.appendBreakline();
                return false;

            });

        }else{
            String values = parameters[2];
            sketch.addInstruction(codeBuffer -> {

                codeBuffer.appendLine(datatype + " " + label + "[]" + " = " + values + ";");
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