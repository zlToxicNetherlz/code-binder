package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.resolver.json.DeviceJson;
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
