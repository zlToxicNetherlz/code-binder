package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonExecutionActionJson;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class DeviceActionJson extends RelationshipJson {

    private String device;
    private String action;
    private String actionType;

}
