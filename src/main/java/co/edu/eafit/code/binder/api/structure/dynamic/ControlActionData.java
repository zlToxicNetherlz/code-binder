package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.ControlActionJson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ControlActionData {

    private String id;
    private String label;

    private ControllerData controllerData;
    private ControlActionJson controlActionJson;

    public ControlActionData(ControlActionJson controlActionJson) {
        this.controlActionJson = controlActionJson;
    }

    public boolean isValid() {
        return controllerData != null && controllerData.isValid();
    }

    public double getValueSetPoint() {
        return controllerData.getSummingPointData().getSetPointData().getValueSetPoint();
    }

    public float getTimeSetPoint() {
        return controllerData.getSummingPointData().getSetPointData().getTime();
    }

}
