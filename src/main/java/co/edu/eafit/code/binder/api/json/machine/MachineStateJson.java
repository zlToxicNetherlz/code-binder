package co.edu.eafit.code.binder.api.json.machine;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MachineStateJson {

    private String id;
    private String type;
    private String label;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

}
