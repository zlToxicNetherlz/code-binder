package co.edu.eafit.code.binder;

import co.edu.eafit.code.binder.api.BinderAPI;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Project;

public class Main {

    public static final boolean DEBUG = true;

    public static void main(String... args) {

        try {

            //BinderAPI.setupServer(DEBUG ? 9100 : 9200);
            Project project = BinderAPI.getLocalProject("ledBoton");
            project.getBoards().get(0).generateCode().printAll();

            PluginLoader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
