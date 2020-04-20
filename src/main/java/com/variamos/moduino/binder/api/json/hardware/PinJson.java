package com.variamos.moduino.binder.api.json.hardware;

import com.variamos.moduino.binder.resolver.json.connections.type.PinType;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PinJson {

    private String id;
    private String label;
    private String type;

    private String link;

    public void linkVariable(String variableName) {
        this.link = variableName;
    }

    public PinType getType() {

        for (PinType type : PinType.values())
            if (type.toString().toLowerCase().equals(type))
                return type;

        return null;

    }

}
