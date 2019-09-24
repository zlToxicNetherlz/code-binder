package co.edu.eafit.code.binder.api.type;

public enum OperatorComponentType {

    GREATHER_OR_EQUALS_THAN(">="),
    LESS_OR_EQUALS_THAN("<="),

    GREATER_THAN(">"),
    LESS_THAN("<"),

    EQUALS("==");

    private String typeLiteral;

    OperatorComponentType(String typeLiteral) {
        this.typeLiteral = typeLiteral;
    }

    public static OperatorComponentType getType(String typeLiteral) {

        for (OperatorComponentType type : OperatorComponentType.values())
            if (type.getTypeLiteral().equals(typeLiteral))
                return type;

        return null;

    }

    public String getTypeLiteral() {
        return typeLiteral;
    }

}
