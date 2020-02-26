package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
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
