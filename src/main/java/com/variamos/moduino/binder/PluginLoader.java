package com.variamos.moduino.binder;

import org.plugface.core.PluginManager;
import org.plugface.core.PluginRef;
import org.plugface.core.factory.PluginManagers;
import org.plugface.core.factory.PluginSources;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class PluginLoader {

    public static void load() throws Exception {

        final PluginManager manager = PluginManagers.defaultPluginManager();
        System.out.println(Paths.get("").toAbsolutePath());
        manager.loadPlugins(PluginSources.jarSource(Paths.get("plugins").toUri()));

        final Collection<PluginRef> plugins = manager.getAllPlugins();

        for (PluginRef pluginRef : plugins) {

            System.out.println("Loading '" + pluginRef.getName() + "'...");

            BinderPlugin binderPlugin = (BinderPlugin) pluginRef.get();
            binderPlugin.enable();

            Map.Entry<Class<?>, LinkedList<Class<?>>> library = binderPlugin.loadClasses();

            System.out.println("Loaded '" + binderPlugin.toString() + "'...");


        }

    }

}
