package com.variamos.moduino.binder.api.json.component;

import com.variamos.moduino.binder.api.json.ComponentJson;
import com.variamos.moduino.binder.api.json.binding.relations.hardware.DeviceBoardJson;
import com.variamos.moduino.binder.api.json.hardware.BoardJson;
import com.variamos.moduino.binder.api.json.hardware.DeviceJson;
import com.variamos.moduino.binder.api.json.hardware.PortJson;
import lombok.ToString;

@ToString
public class HardwareComponentJson extends ComponentJson {

    private BoardJson board;
    private PortJson port; //eliminar este
    private DeviceJson device;
    private DeviceBoardJson relationship_device_board;

    public boolean isBoard() {
        return board != null;
    }

    public boolean isDevice() {
        return device != null;
    }
    public boolean isRelationshipDeviceBoard() {
        return relationship_device_board != null;
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
    public DeviceBoardJson getRelationshipDeviceBoard() {
        return relationship_device_board;
    }

}
