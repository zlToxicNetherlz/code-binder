package com.variamos.moduino.binder.model.library;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchIncludeDirective;

public class SketchHeaderPidLibrary extends SketchIncludeDirective {

    public SketchHeaderPidLibrary() {
        super("PID_v1", true);
    }

}
