package com.variamos.moduino.binder.resolver.processors.macros;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.processors.data.Macro;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

public class BlankVariableMacro extends Macro {

    @Override
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();

        String datatype = parameters[0];
        String label = parameters[1];

        sketch.addInstruction(codeBuffer -> {

            codeBuffer.appendLine(datatype + " " + label + ";");
            return false;

        });

    }

    @Override
    public String getValue() {
        return null;
    }

}