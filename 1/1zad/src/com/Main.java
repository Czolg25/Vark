package com;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readFile() throws IOException {
        boolean spr = true;
        File f = new File(new File(".").getCanonicalPath().replace("1zad","")+"imieniny.holidays");
        String name = "";
        while (name.length() == 0){
            System.out.println("Wpisz imię w dopełniaczu");
            name = scanner.nextLine();
        }
        if(f.exists()){
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if (line.contains(";")){
                    if(line.contains(name)){
                        spr = false;
                        String[] currentNameList = line.split(";");
                        System.out.println("Imieniny w dniu "+currentNameList[1]+"."+currentNameList[0]+": "+currentNameList[2]);
                    }
                }
            }
            if(spr){
                System.out.println("Brak imienia w pliku");
            }
        }
    }
}
