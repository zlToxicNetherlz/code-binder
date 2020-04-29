package com.variamos.moduino.binder.resolver.json.macros;

import com.variamos.moduino.binder.resolver.processors.data.Macro;
import com.variamos.moduino.binder.resolver.processors.macros.*;

public enum MacroType {
    ARRAY(ArrayMacro.class),
    CONSTVARIABLE(ConstVariableMacro.class),
    INCLUDE(IncludeMacro.class),
    DEFINE(DefineMacro.class),
    CUSTOMVARIABLE(CustomVariableMacro.class),
    CALLFROM(CallFromMacro.class),
    BLANKVARIABLE(BlankVariableMacro.class);

    Class clazz;

    MacroType(Class clazz) {
        this.clazz = clazz;
    }

    public Macro process() {

        try {

            Macro macro = (Macro) clazz.newInstance();
            return macro;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("No se ha logrado inicializar el macro '" + clazz.getName() + "'!");
        }

    }

    public static MacroType get(String literal) {

        for (MacroType type : MacroType.values())
            if (type.name().toLowerCase().equals(literal.toLowerCase()))
                return type;

        throw new RuntimeException("El macro '" + literal + "' no existe!");

    }

}
