package co.edu.eafit.code.binder;

import co.edu.eafit.code.binder.api.BinderAPI;
import co.edu.eafit.code.generator.metamodel.arduino.classes.Project;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String... args) throws FileNotFoundException {

        Project project = BinderAPI.getProject();

    }

}
