package co.edu.eafit.code.binder.resolver.json.macros;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class MacroJson {

    private String name;
    private String macro;
    private String output;

    private boolean once;

    private String[] parameters;

    public MacroType getMacroType() {
        return MacroType.get(macro);
    }

    public MacroOutputType getMacroOutputType() {
        return MacroOutputType.get(output);
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

}
