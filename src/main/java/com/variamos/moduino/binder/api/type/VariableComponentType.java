package com.variamos.moduino.binder.api.type;

public enum VariableComponentType {

    ANALOG_VARIABLE("analogVariable"),
    DIGITAL_VARIABLE("digitalVariable"),
    STRING_VARIABLE("stringVariable")

    ;

    private String typeName;

    VariableComponentType(String typeName) {
        this.typeName = typeName;
    }

    public static VariableComponentType getType(String typeName) {

        for (VariableComponentType type : VariableComponentType.values())
            if (type.getTypeName().equals(typeName))
                return type;

        return null;

    }

    public String getTypeName() {
        return typeName;
    }

}
