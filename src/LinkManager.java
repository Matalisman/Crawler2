
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
    Thread[] tablicaWatkow = new Thread[5];
    WebReader[] linkiWatki = new WebReader[5];
    
    public LinkManager(String slowoKluczowe, AtomicInteger linkCounter) {
        this.slowoKluczowe = slowoKluczowe;
        this.linkCounter = linkCounter;
    }
    
    public ArrayList<ObiektCrawlera> pobierzObiekty(){
            
        
        ObjectInputStream content=null ;
        try{
            content = new ObjectInputStream(new FileInputStream("wyniki.dat"));
        } catch(FileNotFoundException e){
        } catch (IOException ex) {
        }
        try{
        obiektyCrawlera = (ArrayList<ObiektCrawlera>) content.readObject();
        }catch (NullPointerException e){
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(SaveResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obiektyCrawlera;
    }
        
    public void stworzWatki(){
        while(true) {
            while(linkCounter.get()<500) {

            obiektyCrawlera = this.pobierzObiekty();

                for (int i=0; i<obiektyCrawlera.size(); ){
                        if(!obiektyCrawlera.get(i).isChecked()){
                            for(int j=0; j<tablicaWatkow.length; j++){
                                try {

                                    if(!tablicaWatkow[j].isAlive()){
                                        linkiWatki[j].setObiekt(obiektyCrawlera.get(i));
                                         System.out.println(" Watek nr wchodzi: " + j);
                                         i++;
                                        tablicaWatkow[j].run();             
                                    }
                                } catch(NullPointerException e){

                                linkiWatki[j]= new WebReader(obiektyCrawlera.get(i), slowoKluczowe, linkCounter);    
                                tablicaWatkow[j] = new Thread(linkiWatki[j]);
                                System.out.println("Utworzono Watek nr: " + j);
                                i++;
                                tablicaWatkow[j].run();
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
    

