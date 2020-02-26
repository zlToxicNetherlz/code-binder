package co.edu.eafit.code.binder.resolver.json.macros;

import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.binder.resolver.processors.macros.CallFromMacro;
import co.edu.eafit.code.binder.resolver.processors.macros.CustomVariableMacro;
import co.edu.eafit.code.binder.resolver.processors.macros.DefineMacro;
import co.edu.eafit.code.binder.resolver.processors.macros.IncludeMacro;

public enum MacroType {
    INCLUDE(IncludeMacro.class),
    DEFINE(DefineMacro.class),
    CUSTOMVARIABLE(CustomVariableMacro.class),
    CALLFROM(CallFromMacro.class);

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
