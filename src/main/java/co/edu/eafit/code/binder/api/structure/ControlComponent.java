package co.edu.eafit.code.binder.api.structure;

import co.edu.eafit.code.binder.api.type.ControlComponentType;
import lombok.ToString;

@ToString
public class ControlComponent<E> {

    private ControlComponentType type;
    private E component;

    public ControlComponent(ControlComponentType type, E component) {
        this.type = type;
        this.component = component;
    }

    public ControlComponentType getType() {
        return type;
    }

    public E getComponent() {
        return component;
    }

    public <T> T getGenericComponent() {
        return (T) component;
    }

}
