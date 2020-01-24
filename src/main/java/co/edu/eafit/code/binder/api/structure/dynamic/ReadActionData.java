package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.ReadActionJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReadActionData {

    private ReadActionJson readActionJson;
    private SketchFunction sketchFunction;

    public ReadActionData(ReadActionJson readActionJson) {
        this.readActionJson = readActionJson;
    }

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

}
