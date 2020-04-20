package com.variamos.moduino.binder.api.json.binding.relations;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OperatorTransitionJson extends RelationshipJson {

    private String logicalOperator;
    private String transition;

}
