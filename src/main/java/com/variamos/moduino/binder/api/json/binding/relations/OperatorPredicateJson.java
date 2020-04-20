package com.variamos.moduino.binder.api.json.binding.relations;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import com.variamos.moduino.binder.api.json.binding.relations.base.CommonPredicateJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class OperatorPredicateJson extends RelationshipJson {

    private String logicalOperator;
    private List<CommonPredicateJson> predicates;

}
