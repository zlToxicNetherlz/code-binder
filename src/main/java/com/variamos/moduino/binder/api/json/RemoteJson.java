package com.variamos.moduino.binder.api.json;

import com.variamos.moduino.binder.api.json.component.BindingComponentJson;
import com.variamos.moduino.binder.api.json.component.ControlComponentJson;
import com.variamos.moduino.binder.api.json.component.HardwareComponentJson;
import com.variamos.moduino.binder.api.json.component.MachineComponentJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class RemoteJson {

    @Getter
    private String name;

    private List<MachineComponentJson> machine;
    private List<HardwareComponentJson> hardware;
    private List<BindingComponentJson> binding;
    private List<ControlComponentJson> control;

    public List<MachineComponentJson> getMachineComponents() {
        return machine;
    }

    public List<HardwareComponentJson> getHardwareComponents() {
        return hardware;
    }

    public List<BindingComponentJson> getBindingComponents() {
        return binding;
    }

    public List<ControlComponentJson> getControlComponents() {
        return control;
    }

}
