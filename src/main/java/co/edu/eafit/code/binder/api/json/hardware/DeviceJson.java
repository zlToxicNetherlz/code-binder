package co.edu.eafit.code.binder.api.json.hardware;

import co.edu.eafit.code.binder.api.type.PortComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceJson {

    private String id;
    private String label;
    private String type;
    private String subType;
    private PinJson[] pins;

    public String getSubType() {
        return subType;
    }

    public PortComponentType getType() {
        return PortComponentType.getType(type);
    }

}
