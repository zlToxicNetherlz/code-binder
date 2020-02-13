package co.edu.eafit.code.binder.api.json.component;

import co.edu.eafit.code.binder.api.json.ComponentJson;
import co.edu.eafit.code.binder.api.json.binding.relations.StateActivityJson;
import co.edu.eafit.code.binder.api.json.binding.relations.hardware.DeviceBoardJson;
import co.edu.eafit.code.binder.api.json.hardware.BoardJson;
import co.edu.eafit.code.binder.api.json.hardware.DeviceJson;
import co.edu.eafit.code.binder.api.json.hardware.PortJson;
import co.edu.eafit.code.binder.api.structure.BindingComponent;
import co.edu.eafit.code.binder.api.type.BindingComponentType;
import lombok.ToString;

import java.util.Map;

@ToString
public class HardwareComponentJson extends ComponentJson {

    private BoardJson board;
    private PortJson port; //eliminar este
    private DeviceJson device;
    private DeviceBoardJson relationship_device_board;

    public boolean isBoard() {
        return board != null;
    }

    public BoardJson getBoard() {
        return board;
    }

    public PortJson getPort() {
        return port;
    }

    public DeviceJson getDevice() {
        return device;
    }

}
