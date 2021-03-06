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
    private AtomicInteger wordCounter;
    private AtomicInteger wBazie;
     SaveResults saveResults;

    WebReader(ObiektCrawlera obiekt, String slowoKluczowe, AtomicInteger atomicInteger,AtomicInteger wordCounter,AtomicInteger wBazie, SaveResults saveResults) {
        this.obiekt = obiekt;
        this.obiekt.setChecked(true);
        this.slowoKluczowe = slowoKluczowe;
        this.atomicInteger = atomicInteger;
        this.saveResults = saveResults;
        this.wordCounter = wordCounter;
        this.wBazie = wBazie;
    }
    
    public ArrayList<ObiektCrawlera> read() {
       String content = null;
           
        System.out.println("Funkcja read())");
        ArrayList<ObiektCrawlera> wynikLinkow = new ArrayList();
        
            content = this.readSource();
       
        
            String[] linki = null;
            String regex = "((https?|http?):((//)|(.))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)|((www.)+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            String regexKeyWord = slowoKluczowe ;
        try {
            
            linki = content.split("=\"|\\s+|>|\"|'|<");
            
                       
            for (int i=0; i<linki.length; i++) {
            
                if (linki[i].matches(regex) && !linki[i].matches(obiekt.getNazwa())) { // matches uses regex
                //System.out.println("Match " + linki[i]);
                wBazie.incrementAndGet();    
                wynikLinkow.add(new ObiektCrawlera(linki[i]));
                }
                if(linki[i].matches(regexKeyWord)) {
                    wordCounter.incrementAndGet();
                }
            
            }}  catch(NullPointerException e){}
        
        try {
            atomicInteger.addAndGet(wynikLinkow.size());
        System.out.println("WebReader");
        System.out.println(atomicInteger);
            saveResults.saveToFile(wynikLinkow,atomicInteger );
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wynikLinkow;
        }

    String readSource() {
        String content = null;
        Scanner scanner = null;
        try {
            URL url;
            if (obiekt.getNazwa().startsWith("www")){
                url = new URL("http://" + obiekt.getNazwa());
            } else {
            url = new URL(obiekt.getNazwa());
            }
            scanner = new Scanner(url.openStream());
            while(scanner.hasNext()) {    
                content += scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Can not read URL");
            e.printStackTrace();
        }
        return content;    
    }

    @Override
    public void run() {
        this.read();
    }

    public ObiektCrawlera getObiekt() {
        return obiekt;
    }

    public void setObiekt(ObiektCrawlera obiekt) {
        this.obiekt = obiekt;
    } 
}