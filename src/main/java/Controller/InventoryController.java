package Controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import entities.Credentials;
import entities.Inventory;
import entities.Users;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Evgeniya
 */
@Named(value = "inventoryController")
@SessionScoped
public class InventoryController implements Serializable {

    @Inject
    private AddRecordController arc;

    @Inject
    private UpdateRecordController urc;

    @Inject
    private Credentials cr;

    @Inject
    private Users user;

    @Inject
    private Inventory inventory;

    private CloseableHttpClient CLIENT = HttpClients.createDefault();
    public static final String AUTH_SERVICE_PATH = "http://localhost:8080/Inventory_RESTServer/webresources/";

    private String inform = "";

    public String getInform() {
        return inform;
    }

    public InventoryController() {
    }

    public void setInform(String info) {
        this.inform = info;
    }

    public Inventory create(Inventory inv) throws JSONException, IOException {
        try {
            CLIENT = HttpClients.createDefault();
//            LOGGER.debug("RESTAuthBean: 'signup' method called ");
            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "inventory/create");
            JSONObject json = new JSONObject();
            json.put("id", inv.getId());
            json.put("email", "you@you");
            json.put("artist", inv.getArtist());
            json.put("album", inv.getAlbum());
            json.put("year", inv.getYear());
            json.put("state", inv.getState());
            json.put("state_detailed", inv.getState_detailed());
            json.put("upc", inv.getUpc());
            json.put("notes", inv.getNotes());
            json.put("status", true);

            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            inv = mapper.readValue((EntityUtils.toString(entity)), Inventory.class);
        } catch (IOException ex) {
//            LOGGER.debug("RESTAuthBean: save user error " + ex.getLocalizedMessage());
        }

        return inv;
    }

    public String update(Inventory inv, int id) {
//        Inventory inv = findInventoryById (id);
        inventory.setId(id);
        inventory.setAlbum(inv.getAlbum());
        inventory.setArtist(inv.getArtist());
        inventory.setYear(inv.getYear());
        inventory.setState(inv.getState());
        inventory.setState_detailed(inv.getState_detailed());
        inventory.setUpc(inv.getUpc());
        inventory.setNotes(inv.getNotes());
        arc.setOk("");
        urc.setOk("");
        this.inform = "";
        return "updateRecord";
    }

    public String delete(Inventory inv, int id) throws JSONException, IOException {
        inventory.setId(id);
        deleteRecord(inv, inventory.getId());
        return "inventory";
    }

    public String getDetails(String state) {
        String details = "";
        switch (state) {
            case "NM":
                details = "Absolutely perfect in every way. Never been played and usually sealed.";
                break;
            case "M":
                details = "The record has been on a shelf between other records. The vinyl looks glossy and clearly has only been played a few times. There are no marks on the vinyl and the whole package is complete.";
                break;
            case "E":
                details = "Same but I’d tolerate very light marks where the vinyl has been in and out of the inner sleeve a few times, or tiny signs of use generally.";
                break;
            case "VG+":
                details = "A few further faults are acceptable, but nothing that really compromises the record visually or audibly. A little rub, light inaudible marks, a little background crackle.";
                break;
            case "VG":
                details = "It’s seen a bit of life, but is still usable. Light pops and clicks, an edge split, light visible scratches. You can still listen to it and enjoy looking at it, but it is visually and audibly USED.";
                break;
            case "G":
                details = "To be honest you’re making trouble for yourself here, as Good means Bad. I’d only be selling something really desirable in this condition, with a bargain price and a full, no holds barred description to match";
                break;
            case "P":
                details = "Attempting to listen will be a disturbing experience. Expect major noise issues, skipping or repeating. The record itself is cracked, badly warped and has deep scratches. The cover is also approaching death.";
                break;
        }
        return details;
    }

    public String add() {

        inventory.setAlbum("");
        inventory.setArtist("");
        inventory.setYear(0);
        inventory.setState("");
        inventory.setState_detailed("");
        inventory.setUpc("");
        inventory.setNotes("");
        arc.setOk("");
        urc.setOk("");
        this.inform = "";
        return "addRecord";
    }

    public Inventory updateRecord(Inventory inv, int id) throws JSONException, IOException {
        Inventory i = null;
        inv.setId(id);
        inv.setEmail(cr.getEmail());
        inv.setStatus(true);
        try {
            CLIENT = HttpClients.createDefault();
//            LOGGER.debug("RESTAuthBean: 'signup' method called ");
            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "inventory/update");
            JSONObject json = new JSONObject();
            json.put("id", inv.getId());
            json.put("email", inv.getEmail());
            json.put("artist", inv.getArtist());
            json.put("album", inv.getAlbum());
            json.put("year", inv.getYear());
            json.put("state", inv.getState());
            json.put("state_detailed", inv.getState_detailed());
            json.put("upc", inv.getUpc());
            json.put("notes", inv.getNotes());
            json.put("status", inv.isStatus());

            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
//            this.inform = EntityUtils.toString(entity);
//            this.inform="";
            if (!EntityUtils.toString(entity).equals("Failed to update a record")) {
                i = inv;
                this.inform = "Record updated!";
//                mapper.readValue((EntityUtils.toString(entity)), Inventory.class);
            } else {
                this.inform = "Failed to update a record! Record already exists!";
            }

        } catch (IOException ex) {
//            LOGGER.debug("RESTAuthBean: save user error " + ex.getLocalizedMessage());
        }

        return i;
    }

    public Inventory deleteRecord(Inventory inv, int id) throws JSONException, IOException {
        Inventory inventory = null;
        inv.setId(id);
        inv.setEmail(cr.getEmail());
        inv.setStatus(false);
        try {
            CLIENT = HttpClients.createDefault();
//            LOGGER.debug("RESTAuthBean: 'signup' method called ");
            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "inventory/update");
            JSONObject json = new JSONObject();
            json.put("id", inv.getId());
            json.put("email", inv.getEmail());
            json.put("artist", inv.getArtist());
            json.put("album", inv.getAlbum());
            json.put("year", inv.getYear());
            json.put("state", inv.getState());
            json.put("state_detailed", inv.getState_detailed());
            json.put("upc", inv.getUpc());
            json.put("notes", inv.getNotes());
            json.put("status", inv.isStatus());

            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
//            this.inform = EntityUtils.toString(entity);
//            this.inform="";
            if (!EntityUtils.toString(entity).equals("Failed to update a record")) {
                inventory = inv;
                this.inform = "";
//                mapper.readValue((EntityUtils.toString(entity)), Inventory.class);
            } else {
                this.inform = "Failed to update a record! Record already exists!";
            }

        } catch (IOException ex) {
//            LOGGER.debug("RESTAuthBean: save user error " + ex.getLocalizedMessage());
        }

        return inventory;
    }

    public ArrayList<Inventory> findUserByEmail(String email) {

        ArrayList<Inventory> inventory = new ArrayList<Inventory>();
        //retrieve list of all users
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/Inventory_RESTServer/webresources/inventory/allbyemail/" + email);
        ClientResponse response = resource.accept("application/json").get(ClientResponse.class
        );
        ArrayList<Inventory> inventories = response.getEntity(new GenericType<ArrayList<Inventory>>() {
        });

        for (Inventory u : inventories) {
            if (email.equals(u.getEmail())) {
                inventory.add(u);
            }
        }
        return inventory;
    }

    public Inventory findInventoryById(int id) {
        Inventory inventory = null;
        ArrayList<Inventory> invs = findUserByEmail(user.getEmail());
        for (Inventory i : invs) {
            if (id == i.getId()) {
                inventory = i;
            }
        }
        return inventory;
    }

    public String signout() {
        cr.setEmail("");
        user.setEmail("");
        return "index";
    }
    
}
