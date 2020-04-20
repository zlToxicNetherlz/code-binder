package com.variamos.moduino.binder.model.variables.data;

public class StaticData {

    public static String KEYPAD_6X6 =
            "char %s[%s][%s] = {\n" +
                    "  {'1', '2', '3', 'A'},\n" +
                    "  {'4', '5', '6', 'B'},\n" +
                    "  {'7', '8', '9', 'C'},\n" +
                    "  {'*', '0', '#', 'D'}\n" +
                    "};";

    public static String ROWPINS = "byte %s[%s] = {%s, %s, %s, %s};";
    public static String COLPINS = "byte %s[%s] = {%s, %s, %s, %s};";

}
