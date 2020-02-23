package co.edu.eafit.code.binder.resolver;

import co.edu.eafit.code.binder.resolver.json.DeviceJson;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class VariamosResolver {

    public static final String ENDPOINT = "";
    public static final String LOCAL = "C:/Users/abrah/Desktop/Integrador/devices.json";

    public static DeviceJson[] resolveJSON() throws FileNotFoundException {

        Gson gson = new Gson();
        DeviceJson[] json = gson.fromJson(new FileReader(LOCAL), DeviceJson[].class);

        return json;

    }

    public static void main(String[] args) throws FileNotFoundException {

        VariamosResolver resolver = new VariamosResolver();
        DeviceJson[] devicesJson = resolver.resolveJSON();

    }

}
