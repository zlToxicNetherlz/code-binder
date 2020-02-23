package co.edu.eafit.code.binder.resolver.json.actions;

import co.edu.eafit.code.binder.resolver.json.actions.instructions.DeviceInstructionJson;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeviceReadActionJson {

    private String name;
    private DeviceInstructionJson[] instructions;

}