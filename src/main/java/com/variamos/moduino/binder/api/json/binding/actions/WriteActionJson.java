package com.variamos.moduino.binder.api.json.binding.actions;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WriteActionJson {

    private String id;
    private String label;
    private String subType;

    private ActionArgumentJson[] arguments;

}
