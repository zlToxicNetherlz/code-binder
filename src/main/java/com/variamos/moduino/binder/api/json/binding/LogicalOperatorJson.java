package com.variamos.moduino.binder.api.json.binding;

import me.itoxic.moduino.metamodel.arduino.entries.sketch.conditions.type.SketchConditionJoinerType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LogicalOperatorJson {

    private String id;
    private String label;
    private String value;

    public SketchConditionJoinerType getJoiner() {

        switch (value) {

            case "AND":
                return SketchConditionJoinerType.AND;

            case "OR":
                return SketchConditionJoinerType.OR;

            default:
                return null;

        }

    }

}
