package com.variamos.moduino.binder.api.json.binding.relations;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import com.variamos.moduino.binder.api.json.binding.relations.base.CommonPhaseJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class StateActivityJson extends RelationshipJson {

    private String state;

    private List<CommonPhaseJson> beginPhase;
    private List<CommonPhaseJson> whilePhase;
    private List<CommonPhaseJson> endPhase;


}
