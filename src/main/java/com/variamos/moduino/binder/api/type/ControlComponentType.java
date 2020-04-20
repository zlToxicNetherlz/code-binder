package com.variamos.moduino.binder.api.type;

import com.variamos.moduino.binder.api.json.component.ControlComponentJson;
import com.variamos.moduino.binder.api.json.control.*;
import com.variamos.moduino.binder.api.json.control.relations.ControllerControlActionJson;
import com.variamos.moduino.binder.api.json.control.relations.SetPointSummingPointJson;
import com.variamos.moduino.binder.api.json.control.relations.SummingPointControllerJson;
import com.variamos.moduino.binder.api.json.control.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.Map;

public enum ControlComponentType {

    SETPOINT("set_point", SetPointJson.class),
    MEASURED_OUTPUT("measured_output", MeasuredOutputJson.class),
    CONTROLLER("controller", ControllerJson.class),
    FILTER("filter", FilterJson.class),
    TRANSDUCER("transducer", TransducerJson.class),
    BRANCHPOINT("branchpoint", BranchPointJson.class),
    SUMMING_POINT("summing_point", SummingPointJson.class),

    CONTROLLER_CONTROLACTION("relationship_controller_controlAction", ControllerControlActionJson.class),
    SET_POINT_SUMMING_POINT("relationship_set_point_summing_point", SetPointSummingPointJson.class),
    SUMMING_POINT_CONTROLLER("relationship_summing_point_controller", SummingPointControllerJson.class);

    private String variableName;
    private Class<?> clazz;

    ControlComponentType(String variableName, Class<?> clazz) {
        this.variableName = variableName;
        this.clazz = clazz;
    }

    public <E> E getGenericComponent(ControlComponentJson componentJson) throws IllegalAccessException {

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

    public static <T> Map.Entry<ControlComponentType, T> getComponent(ControlComponentJson componentJson) {

        for (ControlComponentType type : ControlComponentType.values()) {

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
