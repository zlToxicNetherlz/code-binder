package co.edu.eafit.code.binder.api.type;

import co.edu.eafit.code.binder.api.json.binding.actions.ControlActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.ReadActionJson;
import co.edu.eafit.code.binder.api.json.binding.actions.WriteActionJson;
import co.edu.eafit.code.binder.api.json.binding.instruction.*;
import co.edu.eafit.code.binder.api.json.binding.relations.*;
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
    ACTIVITY_WRITE("relationship_activity_writeAction", ActivityWriteJson.class),
    WRITE_PORT("relationship_writeAction_port", WritePortJson.class),
    CONTROL_ACTION_PORT("relationship_controlAction_port", ControlActionPortJson.class),
    OPERATOR_TRANSITION("relationship_logicalOperator_transition", OperatorTransitionJson.class),
    OPERATOR_PREDICATE("relationship_logicalOperator_predicate", OperatorPredicateJson.class),
    PREDICATE_READ("relationship_predicate_readActions", PredicateReadJson.class),
    READ_PORT("relationship_readAction_port", ReadPortJson.class),

    ;

    private String variableName;
    private Class<?> clazz;

    BindingComponentType(String variableName, Class<?> clazz) {
        this.variableName = variableName;
        this.clazz = clazz;
    }

    public <E> E getGenericComponent(BindingComponentJson componentJson) throws NoSuchFieldException, IllegalAccessException {

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

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return null;

    }

    public Class<?> getClassKind() {
        return clazz;
    }

}
