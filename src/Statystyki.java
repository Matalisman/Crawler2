
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mateusz
 */
public class Statystyki extends TimerTask  {
    
    AtomicInteger liczbaLinkowOczekujacych = new AtomicInteger();
    AtomicInteger liczbaLinkow = new AtomicInteger();
    AtomicInteger wordCounter = new AtomicInteger();
    JLabel output = new JLabel();
    
    Statystyki(AtomicInteger linkCounter, AtomicInteger wordCounter,AtomicInteger liczbaLinkow, JLabel output){
            this.liczbaLinkowOczekujacych=linkCounter;
            this.wordCounter= wordCounter;
            this.liczbaLinkow = liczbaLinkow;
            this.output = output;
    }

    
    @Override
    public void run() {
       String linki = Integer.toString(liczbaLinkowOczekujacych.get());
       String slowa = Integer.toString(wordCounter.get());
       output.setText("<html>Ilosc linkow oczekujacych na zapisanie = " + linki 
               + "<br>Ilosc znalezien podanego slowa w linkach =" + slowa 
                +"<br>Ilosc linkow w bazie =" + liczbaLinkow);
       output.revalidate();
    }
    
}
