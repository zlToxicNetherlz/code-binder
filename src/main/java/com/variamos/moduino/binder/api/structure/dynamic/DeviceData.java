package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.hardware.DeviceJson;
import com.variamos.moduino.binder.resolver.processors.DeviceProcessor;
import lombok.Getter;

@Getter
public class DeviceData {

    private DeviceJson deviceJson;
    private com.variamos.moduino.binder.resolver.json.DeviceJson resolverJson;

    public DeviceData(DeviceProcessor deviceProcessor, DeviceJson deviceJson) {

        this.deviceJson = deviceJson;
        this.resolverJson = deviceProcessor.getDevice(deviceJson.getSubType());

    }

}
