package co.edu.eafit.code.binder.api.json.binding.relations;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WritePortJson extends RelationshipJson {

    private String writeAction;

    private String readPort;
    private String writePort;

}
