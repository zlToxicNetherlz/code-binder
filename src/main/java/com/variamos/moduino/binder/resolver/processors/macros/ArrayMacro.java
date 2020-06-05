package com.variamos.moduino.binder.resolver.processors.macros;

import com.variamos.moduino.binder.resolver.json.macros.MacroJson;
import com.variamos.moduino.binder.resolver.processors.data.Macro;
import me.itoxic.moduino.metamodel.arduino.entries.sketch.Sketch;

/**Clase ArrayMacro que nos permite manejar un arreglo en arduino cuando es necesario para el funcionamiento
 * de un dispositivo (Ej. Keypad)
 * @author Daniel Garcia
 */
public class ArrayMacro extends Macro {

    @Override
    /** Método que define las acciones y agrega al código las instrucciones necesarias
     * para definir un arreglo en Arduino
     */
    public void process(MacroJson json, Sketch sketch) {

        String[] parameters = json.getParameters();
     /** el Json nos va traer los parámetros del arreglo
      * La posición 0 será el tipo de dato del arreglo
      * La posición 1 será el nombre dado al arreglo
      * La posicion 2 y 3 serán el tamaño del arregla con sus valores respectivamente en caso de que
      * el Json que recibimos tenga un tamaño mayor a 3
      * Si tiene un tamaño menor a 3 la posición 2 será los valores del arreglo
      */
        String datatype = parameters[0];
        String label = parameters[1];
        /**Si el arreglo se definió con un tamaño especifico entonces lo declaramos con dicho tamaño
         */
        if(parameters.length>3) {
            String size = parameters[2];
            String values = parameters[3];
            sketch.addInstruction(codeBuffer -> {
                /** Añadimos la instruccion al codigo que estamos generando para arduino
                 */
                codeBuffer.appendLine(datatype + " " + label + "[" + size + "]" + " = " + values + ";");
                codeBuffer.appendBreakline();
                return false;

            });
        }else{
            String values = parameters[2];
            sketch.addInstruction(codeBuffer -> {
                /** Añadimos la instruccion al codigo que estamos generando para arduino
                 */
                codeBuffer.appendLine(datatype + " " + label + "[]" + " = " + values + ";");
                codeBuffer.appendBreakline();
                return false;

            });
        }

    }

    @Override
    /**Devuelve el valor que le indiquemos en caso de ser necesario 
     */
    public String getValue() {
        return null;
    }

}