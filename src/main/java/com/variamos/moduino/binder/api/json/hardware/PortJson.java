package com.variamos.moduino.binder.api.json.hardware;

import com.variamos.moduino.binder.api.type.PortComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PortJson {

    private String id;
    private String label;
    private String type;

    private String[] pin;
    private String initialValue;
    private String subType;

    public String[] getPins() {

        String[] result = new String[pin.length];

        for (int i = 0; i < pin.length; i++)
            result[i] = pin[i].replaceAll("D", "");

        return result;

    }

    public String getSubType() {
        return subType;
    }

    public PortComponentType getType() {
        return PortComponentType.getType(type);
    }

    public int getInitialValue() {
        return Integer.parseInt(initialValue);
    }

}
