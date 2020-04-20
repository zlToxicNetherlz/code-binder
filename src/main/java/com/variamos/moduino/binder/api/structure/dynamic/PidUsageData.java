package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.binding.actions.ControlActionJson;
import lombok.Getter;

@Getter
public class PidUsageData {

    private ControlActionJson controlAction;

    public PidUsageData(ControlActionJson controlAction) {
        this.controlAction = controlAction;
    }

}
