package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.machine.MachineTransitionJson;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BasicTransitionData {

    private final MachineTransitionJson machineTransitionJson;

    private boolean initial;
    private SketchFunction sketchFunction;

    public BasicTransitionData(MachineTransitionJson machineTransitionJson) {
        this.machineTransitionJson = machineTransitionJson;
    }

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

    public void makeInitial() {
        this.initial = true;
    }

}
