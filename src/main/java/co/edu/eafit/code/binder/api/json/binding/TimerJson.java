package co.edu.eafit.code.binder.api.json.binding;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TimerJson {

    private String id;
    private String label;
    private String initialValue;

    public <E> E getInitialValue() {
        return (E) initialValue;
    }

}
