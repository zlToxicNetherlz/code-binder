package com.variamos.moduino.binder.api.json.binding;

import com.variamos.moduino.binder.api.type.VariableComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class VariableJson {

    private String id;
    private String label;
    private String type;
    private String value;

    public <E> E getValue() {
        return (E) value;
    }

    public VariableComponentType getType() {
        return VariableComponentType.getType(type);
    }

}
