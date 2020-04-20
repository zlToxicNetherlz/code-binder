package com.variamos.moduino.binder.resolver.json;

import com.variamos.moduino.binder.resolver.json.actions.DeviceReadActionJson;
import com.variamos.moduino.binder.resolver.json.actions.DeviceWriteActionJson;
import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.json.variables.DeviceVariable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceJson {

    private String name;

    private String[] digitalPins, analogPins, pwmPins;

    private MacroJson[] macros;
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
