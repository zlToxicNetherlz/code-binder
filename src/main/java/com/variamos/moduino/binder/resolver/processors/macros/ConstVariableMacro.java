package com.variamos.moduino.binder.resolver.processors.macros;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.processors.data.Macro;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

public class ConstVariableMacro extends Macro {

    @Override
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();

        String datatype = parameters[0];
        String label = parameters[1];
        String value = parameters[2];

        sketch.addInstruction(codeBuffer -> {

            codeBuffer.appendLine("const "+datatype + " " + label + " = "+value+";");
            codeBuffer.appendBreakline();
            return false;

        });

    }

    @Override
    public String getValue() {
        return null;
    }

}