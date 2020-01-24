package co.edu.eafit.code.binder.api.json.component;

import co.edu.eafit.code.binder.api.json.ComponentJson;
import co.edu.eafit.code.binder.api.json.control.*;
import co.edu.eafit.code.binder.api.json.control.relations.ControllerControlActionJson;
import co.edu.eafit.code.binder.api.json.control.relations.SetPointSummingPointJson;
import co.edu.eafit.code.binder.api.json.control.relations.SummingPointControllerJson;
import co.edu.eafit.code.binder.api.structure.ControlComponent;
import co.edu.eafit.code.binder.api.type.ControlComponentType;
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
