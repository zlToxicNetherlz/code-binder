package co.edu.eafit.code.binder.resolver.json.actions.instructions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeviceInstructionJson {

    private boolean sdk;
    private String fname;

    private boolean optional;
    private String[] parameters;

}
