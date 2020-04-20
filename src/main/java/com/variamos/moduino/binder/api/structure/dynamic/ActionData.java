package com.variamos.moduino.binder.api.structure.dynamic;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ActionData {

    private SketchFunction sketchFunction;

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

}
