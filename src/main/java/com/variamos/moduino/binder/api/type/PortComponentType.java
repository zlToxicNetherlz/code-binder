package com.variamos.moduino.binder.api.type;

public enum PortComponentType {

    ANALOG_SENSOR("analogSensor", true),
    DIGITAL_SENSOR("digitalSensor", true),

    ANALOG_ACTUATOR("analogActuator", false),
    DIGITAL_ACTUATOR("digitalActuator", false);

    private String typeName;
    private boolean sensor;

    PortComponentType(String typeName, boolean sensor) {
        this.typeName = typeName;
        this.sensor = sensor;
    }

    public boolean isDigital() {
        return this == DIGITAL_SENSOR || this == DIGITAL_ACTUATOR;
    }

    public static PortComponentType getType(String typeName) {

        for (PortComponentType type : PortComponentType.values())
            if (type.getTypeName().equals(typeName))
                return type;

        return null;

    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isSensor() {
        return sensor;
    }

}
