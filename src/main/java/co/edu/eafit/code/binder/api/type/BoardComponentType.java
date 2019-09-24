package co.edu.eafit.code.binder.api.type;

public enum BoardComponentType {

    ARDUINO_UNO("ArduinoUNO"),
    ARDUINO_MEGA("ArduinoMega"),

    ;

    private String modelName;

    BoardComponentType(String modelName) {
        this.modelName = modelName;
    }

    public static BoardComponentType getType(String modelName) {

        for (BoardComponentType type : BoardComponentType.values())
            if (type.getModelName().equals(modelName))
                return type;

        return null;

    }

    public String getModelName() {
        return modelName;
    }

}
