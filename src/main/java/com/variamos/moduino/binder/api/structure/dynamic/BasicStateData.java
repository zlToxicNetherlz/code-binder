package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.machine.MachineStateJson;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.SketchFunction;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.preprocessor.SketchDefineDirective;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BasicStateData {

    private final MachineStateJson machineStateJson;
    private final int stateId;

    private boolean initial;
    private SketchFunction sketchFunction;
    private SketchDefineDirective<Integer> sketchDefineDirective;

    public BasicStateData(MachineStateJson machineStateJson, int stateId) {
        this.machineStateJson = machineStateJson;
        this.stateId = stateId;
    }

    public void setDefineDirective(SketchDefineDirective<Integer> defineDirective) {
        this.sketchDefineDirective = defineDirective;
    }

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

    public void makeInitial() {
        this.initial = true;
    }

}
