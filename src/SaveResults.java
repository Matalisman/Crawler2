
import java.io.*;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.*;
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
public class SaveResults {
    private ArrayList<ObiektCrawlera> wyniki;
    private static SaveResults instance = null;
    protected SaveResults() {
      // Exists only to defeat instantiation.
    }
    public static SaveResults getInstance(){
         
      if(instance == null) {
         instance = new SaveResults();
      }

      return instance;
   }
    
    synchronized public void saveToFile(ArrayList<ObiektCrawlera> obiekty, AtomicInteger atomicInteger) throws FileNotFoundException
    {
            
        ArrayList<ObiektCrawlera> obiektyCrawlera = new ArrayList();
        ObjectInputStream content=null ;
        ObjectOutputStream save = null;
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
        try{
        obiektyCrawlera.addAll(obiekty);
        }catch (NullPointerException e){}
        try {
            save = new ObjectOutputStream(new FileOutputStream("wyniki.dat"));
            
        } catch (IOException ex) {
            Logger.getLogger(SaveResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            save.writeObject(obiektyCrawlera);
            System.out.println("SaveResults przed");
            System.out.println(atomicInteger);
            atomicInteger.addAndGet(-(obiekty.size()));
            System.out.println("SaveResults po" + obiektyCrawlera.size());
            System.out.println(atomicInteger);
        } catch (IOException ex) {
            Logger.getLogger(SaveResults.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 

        
        // Close the file
        
    }


