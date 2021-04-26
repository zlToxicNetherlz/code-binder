package com.variamos.moduino.binder;

import com.variamos.moduino.binder.api.BinderAPI;
import me.itoxic.moduino.metamodel.arduino.entries.Project;

public class Main {

    public static final boolean DEBUG = true;

    public static void main(String... args) {

        try {

            //BinderAPI.setupServer(DEBUG ? 9100 : 9200);
            Project project = BinderAPI.getLocalProject("lampara");
            project.getBoards().get(0).generateCode().printAll();

            // PluginLoader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
