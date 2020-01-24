package co.edu.eafit.code.binder.api.json.binding;

import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.conditions.type.SketchConditionJoinerType;
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
