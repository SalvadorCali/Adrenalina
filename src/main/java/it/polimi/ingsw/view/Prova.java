package it.polimi.ingsw.view;

import it.polimi.ingsw.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Prova {
    public static void main(String[] args) throws IOException {
        BufferedReader userInputStream = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer string = new StringTokenizer(userInputStream.readLine());
        if(string.nextToken().equals("ciao")){
            Printer.println(userInputStream.readLine());
            Printer.println(string.nextToken());
        }
        if(string.countTokens() == 2){
            Printer.println(string.nextToken());
            Printer.println(string.nextToken());
        }else{
            Printer.println(string.nextToken());
        }
    }
}
