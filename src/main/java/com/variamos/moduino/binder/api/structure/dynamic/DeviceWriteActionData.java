package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.resolver.json.DeviceJson;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeviceWriteActionData {

    private DeviceJson deviceJson;
    private WriteActionData writeActionData;

}
