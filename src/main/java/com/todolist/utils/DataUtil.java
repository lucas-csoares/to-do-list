package com.todolist.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@UtilityClass
@Schema(description = "Detalhes de operações e atributos referente a Data")
public class DataUtil {

    public final static DateTimeFormatter DATA = DateTimeFormatter.ofPattern ("dd/MM/yyyy");

    public final static DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern ("dd" +
            "/MM/yyyy HH:mm:ss");


    public static String formatarData(LocalDate data) {
        return data != null ? data.format(DATA) : null;
    }


}
