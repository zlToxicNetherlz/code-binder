package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReadPortJson extends RelationshipJson {

    private String readAction;
    private String readPort;

}
