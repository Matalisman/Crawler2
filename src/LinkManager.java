
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
    Thread[] watki = new Thread[20];
    WebReader[] linkiWatki = new WebReader[20];
    
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
        System.out.println(obiektyCrawlera);
        return obiektyCrawlera;
    }
        
    public void stworzWatki(){
        obiektyCrawlera = this.pobierzObiekty();
        for (int i=0; i<20;){
            if(!obiektyCrawlera.get(i).isChecked())
            {
                linkiWatki[1] = new WebReader(obiektyCrawlera.get(i),slowoKluczowe, linkCounter);
                 watki[i] = new Thread(linkiWatki[i]);
                 watki[i].run();
                 i++;
            }
        }
    }

    @Override
    public void run() {
        this.pobierzObiekty();
    }
    
}
