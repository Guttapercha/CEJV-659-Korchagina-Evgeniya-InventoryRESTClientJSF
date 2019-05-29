package Controller;

import entities.Credentials;
import entities.Inventory;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Evgeniya
 */
@Named(value = "addRecordController")
@SessionScoped
public class AddRecordController implements Serializable {

    private String ok = "";
    @Inject
    private InventoryController ic;
      
    private String validation = "";
    private String failed = "";

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    /**
     * Creates a new instance of addRecordController
     */
    public AddRecordController() {
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    private boolean isValidData(Inventory inventory) {
        boolean valid = true;
        String album = inventory.getAlbum();
        String artist = inventory.getArtist();
        String state = inventory.getState();
        String state_detailed = inventory.getState_detailed();
        int year = inventory.getYear();
        if (album.trim().equals("") || artist.trim().equals("") || state.trim().equals("")
                || state_detailed.trim().equals("") || year <= 0) {
            valid = false;
        }
        return valid;
    }

    public String create(Inventory inventory) throws Exception {
//        InventoryController ic = new InventoryController();
        if (!isValidData(inventory)) {
            this.validation = "All fields except upc and notes are mandatory!";
            this.failed = "Record not added!";
            return null;
        } else if (ic.create(inventory) == null ){
            return null;
        } else {
            return "inventory";
        }
    }
    

}
