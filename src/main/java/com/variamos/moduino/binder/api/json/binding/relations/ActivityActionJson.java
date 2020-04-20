package com.variamos.moduino.binder.api.json.binding.relations;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import com.variamos.moduino.binder.api.json.binding.relations.base.CommonExecutionActionJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ActivityActionJson extends RelationshipJson {

    private String activity;
    private List<CommonExecutionActionJson> actions;

}
