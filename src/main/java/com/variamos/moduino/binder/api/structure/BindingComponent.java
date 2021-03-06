package com.variamos.moduino.binder.api.structure;

import com.variamos.moduino.binder.api.type.BindingComponentType;
import lombok.ToString;

@ToString
public class BindingComponent<E> {

    private BindingComponentType type;
    private E component;

    public BindingComponent(BindingComponentType type, E component) {
        this.type = type;
        this.component = component;
    }

    public BindingComponentType getType() {
        return type;
    }

    public E getComponent() {
        return component;
    }

    public <T> T getGenericComponent() {
        return (T) component;
    }

}
