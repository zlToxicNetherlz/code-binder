package co.edu.eafit.code.binder.resolver.processors.macros;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;

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