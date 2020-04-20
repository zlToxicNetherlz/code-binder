package com.variamos.moduino.binder.api.json.machine;

import com.variamos.moduino.binder.api.type.StateComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MachineStateJson {

    private String id;
    private String type;
    private String label;

    public String getId() {
        return id;
    }

    public StateComponentType getType() {
        return StateComponentType.getType(type);
    }

    public String getAsFlag() {
        return "FLAG" + "_" + getLabel().toUpperCase();
    }

    public String getLabel() {
        return label;
    }

}
