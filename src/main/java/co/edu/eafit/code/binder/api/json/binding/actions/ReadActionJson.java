package co.edu.eafit.code.binder.api.json.binding.actions;

import co.edu.eafit.code.binder.api.json.hardware.PinJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReadActionJson {

    private String id;
    private String label;
    private String subType;
    private ActionArgumentJson[] arguments;

}
