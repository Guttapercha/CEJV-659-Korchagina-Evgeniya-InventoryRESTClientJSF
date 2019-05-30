package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

@Named("inventory")
@SessionScoped
@XmlRootElement
public class Inventory implements Serializable {
    
    private String email;
    private String artist;
    private String album;
    private int year;
    private String state;
    private String state_detailed;
    private String upc;
    private String notes;
    private int id;
    boolean status;
    

    public Inventory(int id, String email, String artist, String album, int year, String state, String state_detailed, String upc, String notes, boolean status) {
        this.email = email;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.state = state;
        this.state_detailed = state_detailed;
        this.upc = upc;
        this.notes = notes;
        this.id = id;
        this.status = status;
    }

    public Inventory() { }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState_detailed() {
        return state_detailed;
    }

    public void setState_detailed(String state_detailed) {
        this.state_detailed = state_detailed;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inventory{" + "email=" + email + ", artist=" + artist + ", album=" + album + ", year=" + year + ", state=" + state + ", state_detailed=" + state_detailed + ", upc=" + upc + ", notes=" + notes + ", id=" + id + ", status=" + status + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (this.year != other.year) {
            return false;
        }
        
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.artist, other.artist)) {
            return false;
        }
        if (!Objects.equals(this.album, other.album)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.state_detailed, other.state_detailed)) {
            return false;
        }
        if (!Objects.equals(this.upc, other.upc)) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        return true;
    }
    
    
    
}
