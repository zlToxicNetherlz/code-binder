package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.binding.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OperatorTransitionJson extends RelationshipJson {

    private String logicalOperator;
    private String transition;

}
