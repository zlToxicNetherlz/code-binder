package co.edu.eafit.code.binder.api.type;

import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.conditions.comparator.SketchIntegerComparatorType;

public enum PredicateComponentType {

    GREATHER_OR_EQUALS_THAN(">=", SketchIntegerComparatorType.GREATER_OR_EQUAL),
    LESS_OR_EQUALS_THAN("<=", SketchIntegerComparatorType.LESS_OR_EQUAL),

    GREATER_THAN(">", SketchIntegerComparatorType.GREATER),
    LESS_THAN("<", SketchIntegerComparatorType.LESS),

    EQUALS("==", SketchIntegerComparatorType.EQUALS),
    NOT_EQUALS("!=", SketchIntegerComparatorType.NOT_EQUALS);

    private String typeLiteral;
    private SketchIntegerComparatorType comparatorType;

    PredicateComponentType(String typeLiteral, SketchIntegerComparatorType comparatorType) {
        this.typeLiteral = typeLiteral;
        this.comparatorType = comparatorType;
    }

    public static PredicateComponentType getType(String typeLiteral) {

        for (PredicateComponentType type : PredicateComponentType.values())
            if (type.getTypeLiteral().equals(typeLiteral))
                return type;

        return EQUALS;

    }

    public SketchIntegerComparatorType getComparatorType() {
        return comparatorType;
    }

    public String getTypeLiteral() {
        return typeLiteral;
    }

}
