package co.edu.eafit.code.binder.api.type;

public enum StateComponentType {

    STATE("state"),
    INITIALSTATE("initialState"),

    ;

    private String valueName;

    StateComponentType(String valueName) {
        this.valueName = valueName;
    }

    public static StateComponentType getType(String valueName) {

        for (StateComponentType type : StateComponentType.values())
            if (type.getValueName().equals(valueName))
                return type;

        return null;

    }

    public String getValueName() {
        return valueName;
    }

}
