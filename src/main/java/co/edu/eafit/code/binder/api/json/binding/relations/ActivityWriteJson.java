package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.binding.RelationshipJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonWriteActionJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ActivityWriteJson extends RelationshipJson {

    private String activity;
    private List<CommonWriteActionJson> writeActions;

}
