package Controller;

import entities.Credentials;
import entities.Users;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * Controller class for registration.xhtml
 *
 * @author Evgeniya Korchagina
 */
@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    @Inject
    private UserController uc;

    @Inject
    private Users user;

    @Inject
    private Credentials credentials;

    private String email;

    private String password;

    private String login = "";

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    /**
     * Verifying credentials (email and passsword) based on db record data
     */
    public String login(String email, String password) {

        Credentials credential = new Credentials();
        credential.setEmail(email);
        credential.setPassword(password);
        boolean loggedIn = false;
        String loginPage = null;

        // Password vslidation
        loggedIn = uc.passwordValidation(credential);
        // There is a client so login was successful
        if (!loggedIn) {
            // Unsuccessful login
            this.login = "Wrong Credentials";
        } else {
            loginPage = "inventory.xhtml";
            user.setEmail(email);
            user.setFirstname(uc.findUserByEmail(email).getFirstname());
            user.setLastname(uc.findUserByEmail(email).getLastname());
            credentials.setEmail(email);
            this.login = "";

        }
        return loginPage;
    }
}
