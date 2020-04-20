package com.variamos.moduino.binder.api.structure.dynamic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SummingPointData {

    private String id;
    private String label;
    private String direction;

    private SetPointData setPointData;

    public void createSetPoint(SetPointData setPointData) {
        this.setPointData = setPointData;
    }

    public boolean isValid() {
        return setPointData != null;
    }

}
