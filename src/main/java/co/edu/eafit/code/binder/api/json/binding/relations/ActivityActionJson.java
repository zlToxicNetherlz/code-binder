package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonExecutionActionJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonWriteActionJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ActivityActionJson extends RelationshipJson {

    private String activity;
    private List<CommonExecutionActionJson> actions;

}
