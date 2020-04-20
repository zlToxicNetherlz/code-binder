package com.variamos.moduino.binder.api.json.binding.relations;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceActionJson extends RelationshipJson {

    private String device;
    private String action;
    private String actionType;

}
