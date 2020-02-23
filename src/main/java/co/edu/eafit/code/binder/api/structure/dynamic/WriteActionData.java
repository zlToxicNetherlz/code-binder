package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.WriteActionJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.SketchFunction;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public class WriteActionData {

    private WriteActionJson writeActionJson;
    private SketchFunction sketchFunction;

    private Map<String, String> argumentVariables = new HashMap<String, String>();

    public WriteActionData(WriteActionJson writeActionJson) {
        this.writeActionJson = writeActionJson;
    }

    public void setFunction(SketchFunction sketchFunction) {
        this.sketchFunction = sketchFunction;
    }

}
