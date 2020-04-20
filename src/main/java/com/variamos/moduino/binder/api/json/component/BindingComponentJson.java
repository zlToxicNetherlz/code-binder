package com.variamos.moduino.binder.api.json.component;

import com.variamos.moduino.binder.api.json.ComponentJson;
import com.variamos.moduino.binder.api.json.binding.*;
import com.variamos.moduino.binder.api.json.binding.actions.ControlActionJson;
import com.variamos.moduino.binder.api.json.binding.actions.ReadActionJson;
import com.variamos.moduino.binder.api.json.binding.actions.WriteActionJson;
import com.variamos.moduino.binder.api.json.binding.relations.*;
import com.variamos.moduino.binder.api.json.binding.relations.hardwareBehavior.ActionResultJson;
import com.variamos.moduino.binder.api.json.binding.relations.hardwareBehavior.ActionVariableJson;
import com.variamos.moduino.binder.api.structure.BindingComponent;
import com.variamos.moduino.binder.api.type.BindingComponentType;
import com.variamos.moduino.binder.api.json.binding.*;
import com.variamos.moduino.binder.api.json.binding.relations.*;
import lombok.ToString;

import java.util.Map;

@ToString
public class BindingComponentJson extends ComponentJson {

    private VariableJson variable;
    private ActivityJson activity;

    private TimerJson timer;
    private PredicateJson predicate;
    private LogicalOperatorJson logicalOperator;

    private ReadActionJson readAction;
    private WriteActionJson writeAction;
    private ControlActionJson controlAction;

    private StateActivityJson relationship_state_activity;
    //private ActivityWriteJson relationship_activity_writeAction; //todo: quitar esta por que se reemplazó con ActivityActionJson relationship_activity_action
    private ActivityActionJson relationship_activity_action;
    private WritePortJson relationship_writeAction_port;
    private ControlActionPortJson relationship_controlAction_port;
    private OperatorTransitionJson relationship_logicalOperator_transition;
    private OperatorPredicateJson relationship_logicalOperator_predicate;
    private PredicateVariablesJson relationship_predicate_variables;
    private ReadPortJson relationship_readAction_port;
    private DeviceActionJson relationship_device_action;
    private ActionVariableJson relationship_action_variable;
    private ActionResultJson relationship_action_result;

    public <E> BindingComponent getComponent() {
        Map.Entry<BindingComponentType, E> entry = BindingComponentType.getComponent(this);
        return new BindingComponent(entry.getKey(), entry.getValue());
    }

}
