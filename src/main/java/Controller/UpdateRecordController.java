/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
@Named(value = "updateRecordController")
@RequestScoped
public class UpdateRecordController implements Serializable {

    /**
     * Creates a new instance of UpdateRecordController
     */
    public UpdateRecordController() {
    }

    private String ok = "";
    @Inject
    private InventoryController ic;

    @Inject
    private Inventory inv;

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

    public String update(Inventory inventory) throws Exception {
        int id = inv.getId();
//        InventoryController ic = new InventoryController();
        if (!isValidData(inventory)) {
            this.validation = "All fields except upc and notes are mandatory!";
            this.failed = "Record not updated!";
            return null;
        } else if (ic.updateRecord(inventory, id) == null) {
            return null;
        } else {
            return "inventory";
        }
    }

}
