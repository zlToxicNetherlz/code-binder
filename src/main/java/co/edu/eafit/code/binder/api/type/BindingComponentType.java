package co.edu.eafit.code.binder.api.type;

import co.edu.eafit.code.binder.api.json.binding.*;
import co.edu.eafit.code.binder.api.json.binding.actions.ControlActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.ReadActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.WriteActionJson;
import co.edu.eafit.code.binder.api.json.binding.relations.*;
import co.edu.eafit.code.binder.api.json.binding.relations.hardwareBehavior.ActionResultJson;
import co.edu.eafit.code.binder.api.json.binding.relations.hardwareBehavior.ActionVariableJson;
import co.edu.eafit.code.binder.api.json.component.BindingComponentJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Map;

public enum BindingComponentType {

    VARIABLE("variable", VariableJson.class),
    ACTIVITY("activity", ActivityJson.class),

    TIMER("timer", TimerJson.class),
    PREDICATE("predicate", PredicateJson.class),
    LOGICAL_OPERATOR("logicalOperator", LogicalOperatorJson.class),

    READ_ACTION("readAction", ReadActionJson.class),
    WRITE_ACTION("writeAction", WriteActionJson.class),
    CONTROL_ACTION("controlAction", ControlActionJson.class),

    STATE_ACTIVITY("relationship_state_activity", StateActivityJson.class),
    ACTIVITY_ACTION("relationship_activity_action", ActivityActionJson.class),
    DEVICE_ACTION("relationship_device_action", DeviceActionJson.class),
    WRITE_PORT("relationship_writeAction_port", WritePortJson.class),
    CONTROL_ACTION_PORT("relationship_controlAction_port", ControlActionPortJson.class),
    OPERATOR_TRANSITION("relationship_logicalOperator_transition", OperatorTransitionJson.class),
    OPERATOR_PREDICATE("relationship_logicalOperator_predicate", OperatorPredicateJson.class),
    PREDICATE_VARIABLES("relationship_predicate_variables", PredicateVariablesJson.class),
    ACTION_VARIABLE("relationship_action_variable", ActionVariableJson.class),
    READ_PORT("relationship_readAction_port", ReadPortJson.class),
    ACTION_RESULT("relationship_action_result", ActionResultJson.class),

    ;

    private String variableName;
    private Class<?> clazz;

    BindingComponentType(String variableName, Class<?> clazz) {
        this.variableName = variableName;
        this.clazz = clazz;
    }

    public <E> E getGenericComponent(BindingComponentJson componentJson) throws IllegalAccessException {

        for (Field cacheField : componentJson.getClass().getDeclaredFields())
            if (Modifier.isPrivate(cacheField.getModifiers())) {

                cacheField.setAccessible(true);

                if (cacheField.getName().equals(variableName)) {

                    E value = (E) cacheField.get(componentJson);
                    return value;

                }

            }

        return null;

    }

    public static <T> Map.Entry<BindingComponentType, T> getComponent(BindingComponentJson componentJson) {

        for (BindingComponentType type : BindingComponentType.values()) {

            try {

                T cacheComponent = type.getGenericComponent(componentJson);

                if (cacheComponent != null)
                    return new AbstractMap.SimpleEntry<>(type, cacheComponent);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    public Class<?> getClassKind() {
        return clazz;
    }

}
