package com.variamos.moduino.binder.api.type;

public enum DigitalComponentType {

    LOW("LOW", 0),
    HIGH("HIGH", 1),

    ;

    private String valueName;
    private int valueLiteral;

    DigitalComponentType(String valueName, int valueLiteral) {
        this.valueName = valueName;
        this.valueLiteral = valueLiteral;
    }

    public static DigitalComponentType getType(String valueName) {

        for (DigitalComponentType type : DigitalComponentType.values())
            if (type.getValueName().equals(valueName))
                return type;

        return null;

    }

    public String getValueName() {
        return valueName;
    }

    public int getValueLiteral() {
        return valueLiteral;
    }

}
