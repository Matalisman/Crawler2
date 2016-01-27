
import java.io.Serializable;

/**
 *
 * @author Mateusz
 */
public class ObiektCrawlera implements Serializable {
    private boolean checked = false;
    private String nazwa;
    
    ObiektCrawlera(String nazwa){
            this.setNazwa(nazwa);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
