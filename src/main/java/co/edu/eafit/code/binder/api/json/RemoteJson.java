package co.edu.eafit.code.binder.api.json;

import co.edu.eafit.code.binder.api.json.component.BindingComponentJson;
import co.edu.eafit.code.binder.api.json.component.ControlComponentJson;
import co.edu.eafit.code.binder.api.json.component.HardwareComponentJson;
import co.edu.eafit.code.binder.api.json.component.MachineComponentJson;
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
