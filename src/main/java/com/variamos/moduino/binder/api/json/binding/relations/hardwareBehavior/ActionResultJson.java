package com.variamos.moduino.binder.api.json.binding.relations.hardwareBehavior;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ActionResultJson extends RelationshipJson {
    private String variable;
    private String action;
}
