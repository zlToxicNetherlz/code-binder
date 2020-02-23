package co.edu.eafit.code.binder.resolver.json.variables;

public enum DeviceVariableType {
    STATIC("static"),
    DYNAMIC("dynamic"),

    DIGITALPIN("digitalpin"),
    ANALOGPIN("analogpin"),
    PWMPIN("pwmpin");

    private String value;

    DeviceVariableType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
