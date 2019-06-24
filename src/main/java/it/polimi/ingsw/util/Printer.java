package it.polimi.ingsw.util;

/**
 * Contains methods to print.
 */
public class Printer {
    /**
     * Class constructor.
     */
    private Printer(){}

    /**
     * Replaces the System.out.print() method.
     * @param object an object to print.
     */
    public static void print(Object object){
        System.out.print(object);
    }

    /**
     * Replaces the System.out.println() method.
     * @param object an object to print.
     */
    public static void println(Object object){
        System.out.println(object);
    }

    /**
     * Replaces the printStackTrace() method.
     * @param e an exception.
     */
    public static void err(Exception e){
        e.printStackTrace();
    }
}
