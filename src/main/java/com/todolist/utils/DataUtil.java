package com.todolist.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataUtil {

    public final static DateTimeFormatter DATA = DateTimeFormatter.ofPattern ("dd/MM/yyyy");

    public final static DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern ("dd" +
            "/MM/yyyy HH:mm:ss");


    public static String formatarData(LocalDate data) {
        return data != null ? data.format(DATA) : null;
    }


}
