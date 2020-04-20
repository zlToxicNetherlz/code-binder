package com.variamos.moduino.binder.api.json.component;

import com.variamos.moduino.binder.api.json.ComponentJson;
import com.variamos.moduino.binder.api.json.machine.MachineStateJson;
import com.variamos.moduino.binder.api.json.machine.MachineTransitionJson;
import lombok.ToString;

@ToString
public class MachineComponentJson extends ComponentJson {

    private MachineStateJson state;
    private MachineTransitionJson transition;

    public boolean isState() {
        return state != null;
    }

    public MachineStateJson getState() {
        return state;
    }

    public MachineTransitionJson getTransition() {
        return transition;
    }

}
