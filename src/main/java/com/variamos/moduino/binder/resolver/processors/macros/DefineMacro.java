package com.variamos.moduino.binder.resolver.processors.macros;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.processors.data.Macro;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;

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
