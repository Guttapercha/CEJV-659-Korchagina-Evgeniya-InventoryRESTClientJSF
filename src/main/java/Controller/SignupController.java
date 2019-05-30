package Controller;

import entities.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * Controller class for registration.xhtml
 *
 * @author Evgeniya Korchagina
 */
@Named("signupController")
@RequestScoped
public class SignupController implements Serializable {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private String emailinuse = "";
    private String ok = "";
    private String password2 = "";
    private String passwordmatch = "";

    @Inject
    private UserController uc;

    @Inject
    private Users u;

    public String getPasswordmatch() {
        return passwordmatch;
    }

    public void setPasswordmatch(String passwordmatch) {
        this.passwordmatch = passwordmatch;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public String getEmailinuse() {
        return emailinuse;
    }

    /*
    this method verifies matching initial and confirmed passwords
     */
    private boolean isValidPassword(Users user) {
        boolean valid = true;
        String password = user.getPassword();
        if (!password.equals(this.password2)) {
            this.passwordmatch = "Passwords do not match";
            valid = false;
        }
        if (password.trim().equals("")) {
            this.passwordmatch = "Empty password!";
            valid = false;
        }
        return valid;
    }

    /**
     * This method checks if the email address is already exists and whether it
     * has allowed format
     *
     * @return A boolean value.
     */
    private boolean isValidEmail(Users user) {
        boolean valid = false;
        String email = user.getEmail().trim();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        if (email != null) {
            if (uc.findUserByEmail(email) == null) {
                valid = true;
            } else if (!matcher.find()) {
                valid = false;
                this.emailinuse = "Invalid email";
            } else {
                this.emailinuse = "Provide another email";
            }
        }
        return valid;
    }
    public void setToEmpty() {
        u.setFirstname("");
        u.setLastname("");
        u.setStreet("");
        u.setCity("");
        u.setCountry("");
        u.setProvince("");
        u.setFirstname("");
        u.setPostal("");
        u.setEmail("");        
    }
    
    public String goToRegistration () {
        setToEmpty();
        return "registration";
    }
   

    /**
     * Perform email check and if successful then move on to registration.xhtml
     *
     * @return
     * @throws Exception
     */
    public String signup(Users user) throws Exception {
        if (isValidEmail(user) && isValidPassword(user)) {
            uc.signup(user);
            this.ok = "Success!";
            setToEmpty();
            return null;
        } 
        return null;
    }
    
}
