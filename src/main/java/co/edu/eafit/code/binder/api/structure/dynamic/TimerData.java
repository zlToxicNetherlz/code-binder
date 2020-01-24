package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.TimerJson;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.variables.SketchLongVariable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TimerData {

    private TimerJson timerJson;
    private SketchLongVariable sketchLongVariable;

    public TimerData(TimerJson timerJson) {
        this.timerJson = timerJson;
    }

    public void setVariable(SketchLongVariable sketchLongVariable) {
        this.sketchLongVariable = sketchLongVariable;
    }

}
