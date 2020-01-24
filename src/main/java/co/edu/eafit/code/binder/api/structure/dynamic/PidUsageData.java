package co.edu.eafit.code.binder.api.structure.dynamic;

import co.edu.eafit.code.binder.api.json.binding.actions.ControlActionJson;
import lombok.Getter;

@Getter
public class PidUsageData {

    private ControlActionJson controlAction;

    public PidUsageData(ControlActionJson controlAction) {
        this.controlAction = controlAction;
    }

}
