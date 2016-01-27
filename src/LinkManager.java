
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mateusz
 */
public class LinkManager implements Runnable {
    
    ArrayList<ObiektCrawlera> obiektyCrawlera = new ArrayList();
    String slowoKluczowe;
    AtomicInteger linkCounter;
    SaveResults saveResults = new SaveResults();
    Thread[] tablicaWatkow = new Thread[10];
    WebReader[] linkiWatki = new WebReader[10];
    
    public LinkManager(String slowoKluczowe, AtomicInteger linkCounter, SaveResults saveResults) {
        this.slowoKluczowe = slowoKluczowe;
        this.linkCounter = linkCounter;
        this.saveResults=saveResults;
    }
    
    public ArrayList<ObiektCrawlera> pobierzObiekty(){
        try {
            saveResults.saveToFile(null, linkCounter);
        } catch (FileNotFoundException ex) {
            System.out.println("nie znalazł mi");
        }
        return saveResults.getList();
    }
        
    public void stworzWatki(){
        while(true) {
            while(linkCounter.get()<500) {

            obiektyCrawlera = this.pobierzObiekty();

                for (int i=0; i<obiektyCrawlera.size(); i++){
                        if(!obiektyCrawlera.get(i).isChecked()){
                            for(int j=0; j<tablicaWatkow.length; j++){
                                try {

                                    if(!tablicaWatkow[j].isAlive()){
                                        linkiWatki[j].setObiekt(obiektyCrawlera.get(i));
                                         System.out.println(" Watek nr wchodzi: " + j);
                                         System.out.println(obiektyCrawlera.get(i).getNazwa());
                                        tablicaWatkow[j].run();
                                        break;
                                    }
                                } catch(NullPointerException e){

                                linkiWatki[j]= new WebReader(obiektyCrawlera.get(i), slowoKluczowe, linkCounter, saveResults);    
                                tablicaWatkow[j] = new Thread(linkiWatki[j]);
                                System.out.println("Utworzono Watek nr: " + j);
                                System.out.println(obiektyCrawlera.get(i).getNazwa());
                                tablicaWatkow[j].start();
                                break;
                                }
                            }    
                        }
                }
                 System.out.println("A linkManager sobie w watku dziala ");
            }
           
        }
    }
    
    @Override
    public void run() {
        this.stworzWatki();
    }
}
    

