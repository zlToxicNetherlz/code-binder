package co.edu.eafit.code.binder.api.json.machine;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MachineTransitionJson {

    private String id;
    private String label;

    private String source;
    private String target;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

}
