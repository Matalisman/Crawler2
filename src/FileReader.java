/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Mateusz
 */
public class FileReader 
{
    
    private String path;

    FileReader(String path) {
        this.path = path;
    }
    
    public String read() 
    {
        System.out.println("Funkcja read())");
        String output ="";
        String content = null;
        
        content = this.readSource();
        
            String[] slowa;
            slowa = content.split("\\s+");
             
            String regex = "((https?|http?):((//)|(.))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)|((www.)+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
            
            
            for (int i=0; i<slowa.length; i++) {
            if (slowa[i].matches(regex)) { // matches uses regex
               
            System.out.println("Match " + slowa[i]);
            output = output + slowa[i] + " ";
            //output.add(slowa[i]); Ti chce dodac do arraya wszystkie linki
            }
            //jCreate.setoutputArray(output); tu chce przekazac dane do widoku
        }
        
            return output;
    }    

    String readSource() {
        System.out.println("Funkcja readSource())");
        
        String content = null;
        
        try {            
            content = new String(Files.readAllBytes(Paths.get(path)));
                } catch (FileNotFoundException e) {
                e.printStackTrace();
                } catch (IOException ex) {
                Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
                }
        return content;
    }
}
    