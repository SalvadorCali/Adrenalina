package it.polimi.ingsw.util;

public class Printer {
    private Printer(){}

    public static void print(Object object){
        System.out.print(object);
    }

    public static void println(Object object){
        System.out.println(object);
    }

    public static void err(Exception e){
        e.printStackTrace();
    }
}
