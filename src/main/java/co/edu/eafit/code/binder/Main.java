package co.edu.eafit.code.binder;

import co.edu.eafit.code.binder.api.BinderAPI;

public class Main {

    public static final boolean DEBUG = true;

    public static void main(String... args) {

        try {

            BinderAPI.setupServer(DEBUG ? 9100 : 9200);
            PluginLoader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
