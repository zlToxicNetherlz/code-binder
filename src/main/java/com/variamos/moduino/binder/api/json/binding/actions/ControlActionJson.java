package com.variamos.moduino.binder.api.json.binding.actions;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ControlActionJson {

    private String id;
    private String label;
    private String type;
    private String controlType;

    private ActionArgumentJson[] arguments;
    private ConfigurationArgumentJson[] configuration;

    public boolean isContinuous() {
        return controlType.equals("continuous");
    }

}
