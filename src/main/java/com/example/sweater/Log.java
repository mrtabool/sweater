package com.example.sweater;

import java.util.Arrays;

public class Log {

    public static void debug(String data) {
        System.out.println(data);
    }

    public static void debug(String[] data) {
        System.out.println(Arrays.toString(data));
    }

}
