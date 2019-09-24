package co.edu.eafit.code.binder.api.json.hardware;

import co.edu.eafit.code.binder.api.type.PortComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PortJson {

    private String id;
    private String label;
    private String type;

    private String pin;
    private String initialValue;

    public String getPin() {

        if (getType().isDigital() && pin.contains("D"))
            return pin.replace("D", "");

        return pin;

    }

    public PortComponentType getType() {
        return PortComponentType.getType(type);
    }

    public int getInitialValue() {
        return Integer.parseInt(initialValue);
    }

}
