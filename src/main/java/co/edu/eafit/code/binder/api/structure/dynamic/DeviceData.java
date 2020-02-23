package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.hardware.DeviceJson;
import co.edu.eafit.code.binder.resolver.processors.DeviceProcessor;
import lombok.Getter;

@Getter
public class DeviceData {

    private DeviceJson deviceJson;
    private co.edu.eafit.code.binder.resolver.json.DeviceJson resolverJson;

    public DeviceData(DeviceProcessor deviceProcessor, DeviceJson deviceJson) {

        this.deviceJson = deviceJson;
        this.resolverJson = deviceProcessor.getDevice(deviceJson.getSubType());

    }

}
