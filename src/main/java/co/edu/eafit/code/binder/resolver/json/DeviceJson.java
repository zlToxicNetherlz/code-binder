package co.edu.eafit.code.binder.resolver.json;

import co.edu.eafit.code.binder.resolver.json.actions.DeviceReadActionJson;
import co.edu.eafit.code.binder.resolver.json.actions.DeviceWriteActionJson;
import co.edu.eafit.code.binder.resolver.json.variables.DeviceVariable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceJson {

    private String name;

    private String[] digitalPins, analogPins, pwmPins;
    private DeviceVariable[] variables;

    private DeviceWriteActionJson[] writeActions;
    private DeviceReadActionJson[] readActions;

    public boolean needDigitalPins() {
        return digitalPins != null;
    }

    public boolean needAnalogPins() {
        return analogPins != null;
    }

    public boolean needPWMPins() {
        return pwmPins != null;
    }

}
