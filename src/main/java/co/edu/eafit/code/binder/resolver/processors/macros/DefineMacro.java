package co.edu.eafit.code.binder.resolver.processors.macros;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preprocessor.SketchDefineDirective;

public class DefineMacro extends Macro {

    @Override
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();

        String label = parameters[0].toUpperCase();
        String value = parameters[1];

        SketchDefineDirective<String> defineDirective = new SketchDefineDirective<>(label, value);
        sketch.addPreprocessorDirective(defineDirective);

    }

    @Override
    public String getValue() {
        return null;
    }

}
