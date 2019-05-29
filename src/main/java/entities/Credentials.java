package entities;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlRootElement;

@Named("credentials")
@SessionScoped
@XmlRootElement
public class Credentials implements Serializable {
    
    private String email;
    private String password;
    
    public Credentials() {
        
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;    
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
    
}

