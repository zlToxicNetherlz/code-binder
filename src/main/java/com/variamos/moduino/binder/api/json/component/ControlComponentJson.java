package com.variamos.moduino.binder.api.json.component;

import com.variamos.moduino.binder.api.json.ComponentJson;
import com.variamos.moduino.binder.api.json.control.*;
import com.variamos.moduino.binder.api.json.control.relations.ControllerControlActionJson;
import com.variamos.moduino.binder.api.json.control.relations.SetPointSummingPointJson;
import com.variamos.moduino.binder.api.json.control.relations.SummingPointControllerJson;
import com.variamos.moduino.binder.api.structure.ControlComponent;
import com.variamos.moduino.binder.api.type.ControlComponentType;
import com.variamos.moduino.binder.api.json.control.*;
import lombok.ToString;

import java.util.Map;

@ToString
public class ControlComponentJson extends ComponentJson {

    private SetPointJson set_point;
    private MeasuredOutputJson measured_output;
    private ControllerJson controller;
    private FilterJson filter;
    private TransducerJson transducer;
    private BranchPointJson branchpoint;
    private SummingPointJson summing_point;

    private ControllerControlActionJson relationship_controller_controlAction;
    private SetPointSummingPointJson relationship_set_point_summing_point;
    private SummingPointControllerJson relationship_summing_point_controller;

    public <E> ControlComponent getComponent() {
        Map.Entry<ControlComponentType, E> entry = ControlComponentType.getComponent(this);
        return new ControlComponent(entry.getKey(), entry.getValue());
    }

}
