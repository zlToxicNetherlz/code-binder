package co.edu.eafit.code.binder.resolver.processors.macros;

import co.edu.eafit.code.binder.resolver.json.macros.MacroJson;
import co.edu.eafit.code.binder.resolver.processors.data.Macro;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.Sketch;
import co.edu.eafit.code.generator.metamodel.arduino.classes.sketch.preprocessor.SketchIncludeDirective;

import java.util.LinkedList;

public class IncludeMacro extends Macro {

    private static LinkedList<String[]> included = new LinkedList<>();

    @Override
    public void process(MacroJson json, Sketch sketch) {

        if (json.isOnce() && has(json.getParameters()))
            return;
        else if (json.isOnce())
            included.add(json.getParameters());


        String[] parameters = json.getParameters();

        String label = parameters[0];
        String header = parameters[1];

        SketchIncludeDirective defineDirective = new SketchIncludeDirective(label, Boolean.valueOf(header));
        sketch.addPreprocessorDirective(defineDirective);

    }

    public static void clearIncluded() {
        included.clear();
    }

    public static boolean has(String[] parameters) {
        z:
        for (int i = 0; i < included.size(); i++) {
            String[] inc = included.get(i);
            if (inc.length == parameters.length) {
                for (int j = 0; j < inc.length; j++) {
                    if (inc[j] != parameters[j])
                        continue z;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getValue() {
        return null;
    }

}