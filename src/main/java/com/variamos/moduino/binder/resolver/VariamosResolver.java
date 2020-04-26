package com.variamos.moduino.binder.resolver;

import com.variamos.moduino.binder.resolver.json.DeviceJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class VariamosResolver {

    public static final String ENDPOINT = "https://cdn.itoxic.me/devices.json";
    // public static final String LOCAL = "C:/Users/abrah/Desktop/Integrador/devices.json";

    public static DeviceJson[] resolveJSON() throws Exception {



        Gson gson = new Gson();
        DeviceJson[] json = gson.fromJson(getRemoteData(), DeviceJson[].class);

        return json;

    }

    public static String getRemoteData() throws Exception {

        try {

            URL url = new URL(ENDPOINT);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            StringBuilder textBuilder = new StringBuilder();

            try (Reader reader = new BufferedReader(new InputStreamReader
                    (con.getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c;
                while ((c = reader.read()) != -1)
                    textBuilder.append((char) c);
            }

            return textBuilder.toString();

        } catch (Exception e) {
            System.out.println("No se lograron recibir datos del ENDPOINT :: " + ENDPOINT + ", para la lectura de los dispositivos.");
            throw e;
        }

    }

    public static void main(String[] args) throws FileNotFoundException {

        VariamosResolver resolver = new VariamosResolver();

    }

}
