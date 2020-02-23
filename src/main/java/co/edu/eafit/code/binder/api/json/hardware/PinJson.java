package co.edu.eafit.code.binder.api.json.hardware;

import co.edu.eafit.code.binder.resolver.json.connections.type.PinType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PinJson {

    private String id;
    private String label;
    private String type;

    public PinType getType() {

        for (PinType type : PinType.values())
            if (type.toString().toLowerCase().equals(type))
                return type;

        return null;

    }

}
