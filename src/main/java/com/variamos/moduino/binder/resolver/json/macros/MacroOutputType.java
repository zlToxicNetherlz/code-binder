package com.variamos.moduino.binder.resolver.json.macros;

public enum MacroOutputType {

    FIRST,
    PIN,
    DIRECTIVE,
    VARIABLES,
    SETUP;

    public static MacroOutputType get(String literal) {

        for (MacroOutputType type : MacroOutputType.values())
            if (type.name().toLowerCase().equals(literal.toLowerCase()))
                return type;

        throw new RuntimeException("La salida '" + literal + "' para macros no existe!");

    }

}
