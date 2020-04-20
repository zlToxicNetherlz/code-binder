package com.variamos.moduino.binder.api.json.binding.relations.hardware;

import com.variamos.moduino.binder.api.json.RelationshipJson;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DeviceBoardJson  extends RelationshipJson {
    private String pinDevice;
    private String pinBoard;
}
