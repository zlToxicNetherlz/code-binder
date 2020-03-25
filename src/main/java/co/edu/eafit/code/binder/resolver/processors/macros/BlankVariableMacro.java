package co.edu.eafit.code.binder.resolver.processors.macros;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;

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