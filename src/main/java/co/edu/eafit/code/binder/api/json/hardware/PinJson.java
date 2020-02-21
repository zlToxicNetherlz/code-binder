package co.edu.eafit.code.binder.api.json.hardware;

import co.edu.eafit.code.binder.api.type.PortComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PinJson {

    private String id;
    private String label;
    private String type;
}
