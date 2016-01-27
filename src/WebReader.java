/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mateusz
 */
public class WebReader implements  Runnable {

    private ObiektCrawlera obiekt;
    private String slowoKluczowe;
    private AtomicInteger atomicInteger;

    WebReader(ObiektCrawlera obiekt, String slowoKluczowe, AtomicInteger atomicInteger) {
        this.obiekt = obiekt;
        this.slowoKluczowe = slowoKluczowe;
        this.atomicInteger = atomicInteger;
    }
    
    

    /**
     *
     * @return
     */
    
    public ArrayList<ObiektCrawlera> read() {
       String content = null;
           
        System.out.println("Funkcja read())");
        ArrayList<ObiektCrawlera> wynikLinkow = new ArrayList();
        
            content = this.readSource();
       
        
            String[] linki = null;
            String regex = "((https?|http?):((//)|(.))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)|((www.)+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            String regexKeyWord = slowoKluczowe ;
        try {
            
            linki = content.split("=\"|\\s+|>|\"|'");
            
                       
            for (int i=0; i<linki.length; i++) {
            
                if (linki[i].matches(regex)) { // matches uses regex
                //System.out.println("Match " + linki[i]);
                wynikLinkow.add(new ObiektCrawlera(linki[i]));
                }
            
            }}  catch(NullPointerException e){}
        
        SaveResults zapisz = new SaveResults();
        try {
            atomicInteger.addAndGet(wynikLinkow.size());
        System.out.println("WebReader");
        System.out.println(atomicInteger);
            zapisz.saveToFile(wynikLinkow,atomicInteger );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wynikLinkow;
        }

    String readSource() {
        String content = null;
        Scanner scanner = null;
        try {
            URL url = new URL(obiekt.getNazwa());
            scanner = new Scanner(url.openStream());
            }
        catch (Exception e) {
            System.out.println("Can not read URL");
            e.printStackTrace();
            }
            
        
        while(scanner.hasNext())
            {    
            content += scanner.nextLine();
            }
        
        return content;    
    }

    @Override
    public void run() {
        this.read();
    }
}