package com.variamos.moduino.binder.api.structure.dynamic;

import com.variamos.moduino.binder.api.json.binding.actions.WriteActionJson;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public class WriteActionData extends ActionData {

    private WriteActionJson writeActionJson;
    private Map<String, String> argumentVariables = new HashMap<String, String>();

    public WriteActionData(WriteActionJson writeActionJson) {
        this.writeActionJson = writeActionJson;
    }

    public WriteActionJson getJson() {
        return writeActionJson;
    }

}
