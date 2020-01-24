package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.machine.MachineTransitionJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
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
