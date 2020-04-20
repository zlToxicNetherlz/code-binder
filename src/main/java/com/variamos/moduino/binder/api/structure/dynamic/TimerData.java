package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.binding.TimerJson;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.variables.SketchLongVariable;
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
