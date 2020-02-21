package co.edu.eafit.code.binder.api.json.binding.relations.hardwareBehavior;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ActionVariableJson extends RelationshipJson {
    private String variable;
    private String actionArgument;
}
