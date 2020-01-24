package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.WriteActionJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WriteActionData {

    private WriteActionJson writeActionJson;
    private SketchFunction sketchFunction;

    public WriteActionData(WriteActionJson writeActionJson) {
        this.writeActionJson = writeActionJson;
    }

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

}
