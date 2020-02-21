package co.edu.eafit.code.binder.api.json.binding.relations.hardware;

import co.edu.eafit.code.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceBoardJson  extends RelationshipJson {
    private String pinDevice;
    private String pinBoard;
}
