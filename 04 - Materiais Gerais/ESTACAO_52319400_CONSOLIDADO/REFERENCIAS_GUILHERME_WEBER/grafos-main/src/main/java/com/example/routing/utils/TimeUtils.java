package com.example.routing.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    public static double horasParaMinutos(double horas) {
        return horas * 60;
    }
    
    public static double minutosParaHoras(double minutos) {
        return minutos / 60;
    }
    
    public static String formatarTempo(double horas) {
        int horasInt = (int) horas;
        int minutos = (int) ((horas - horasInt) * 60);
        return String.format("%02d:%02d", horasInt, minutos);
    }
    
    public static LocalTime adicionarHoras(LocalTime tempo, double horas) {
        long minutos = (long) (horas * 60);
        return tempo.plusMinutes(minutos);
    }
}
