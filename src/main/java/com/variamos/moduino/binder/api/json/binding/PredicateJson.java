package com.variamos.moduino.binder.api.json.binding;

import com.variamos.moduino.binder.api.type.PredicateComponentType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PredicateJson {

    private String id;
    private String label;
    private String operator;

    public PredicateComponentType getOperator() {
        return PredicateComponentType.getType(operator);
    }

}
