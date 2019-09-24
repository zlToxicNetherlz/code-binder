package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.binding.RelationshipJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonPredicateJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class OperatorPredicateJson extends RelationshipJson {

    private String logicalOperator;
    private List<CommonPredicateJson> predicates;

}
