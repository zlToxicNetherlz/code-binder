package com.variamos.moduino.binder;

import java.util.LinkedList;
import java.util.Map;

public interface BinderPlugin {

    void enable();

    void disable();

    Map.Entry<Class<?>, LinkedList<Class<?>>> loadClasses();

}
