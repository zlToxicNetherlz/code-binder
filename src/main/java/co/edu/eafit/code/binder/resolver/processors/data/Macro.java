package co.edu.eafit.code.binder.resolver.processors.data;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;

public abstract class Macro {

    public abstract void process(MacroJson json, Sketch sketch);

    public abstract String getValue();

}
