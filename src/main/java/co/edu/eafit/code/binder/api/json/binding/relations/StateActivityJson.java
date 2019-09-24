package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.binding.RelationshipJson;
import co.edu.eafit.code.binder.api.json.binding.relations.base.CommonPhaseJson;
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
