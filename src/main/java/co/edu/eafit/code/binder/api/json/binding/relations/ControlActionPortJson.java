package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.binding.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ControlActionPortJson extends RelationshipJson {

    private String controlAction;

    private String readPort;
    private String writePort;

    private String timer;
    private String varTimerLimit;

}
