package com.variamos.moduino.binder.resolver.processors.data;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

/**
 * Declaracion clase Macro que permite darle estructura a un trozo de código precargado.
 * Un macro es un trozo de código precargado necesario para el correcto funcionamiento
 * de componentes basicos en arduino.
 *
 * @author Abraham Lora
 *
 */
public abstract class Macro {
    /**
     * Metodo donde se haran las operaciones correspondientes a cada macro y se añadiran al codigo generado.
     *
     * @param json obtiene el macro en formato JSON con sus componentes especificos de un dispositivo
     * @param sketch recibe el bosquejo del codigo en arduino y nos permite añadirle lineas de instrucciones
     *
     */
    public abstract void process(MacroJson json, Sketch sketch);

    /**
     * Metodo de apoyo para alguna operacion extra que necesite recuperar un valor de un macro especifico
     */
    public abstract String getValue();

}
