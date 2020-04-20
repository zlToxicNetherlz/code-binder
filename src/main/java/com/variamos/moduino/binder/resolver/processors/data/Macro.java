package com.variamos.moduino.binder.resolver.processors.data;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

public abstract class Macro {

    public abstract void process(MacroJson json, Sketch sketch);

    public abstract String getValue();

}
