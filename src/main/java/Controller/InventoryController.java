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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
        Inventory inventory = null;
        inv.setId(lastId() + 1);
        inv.setEmail(cr.getEmail());
        inv.setStatus(true);
        try {
            CLIENT = HttpClients.createDefault();
//            LOGGER.debug("RESTAuthBean: 'signup' method called ");
            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "inventory/create");
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
            if (!EntityUtils.toString(entity).equals("Failed to create a record")) {
                inventory = inv;
                this.inform = "Record created!";
//                mapper.readValue((EntityUtils.toString(entity)), Inventory.class);
            } else {
                this.inform = "Failed to create a record! Record already exists!";
            }

        } catch (IOException ex) {
//            LOGGER.debug("RESTAuthBean: save user error " + ex.getLocalizedMessage());
        }

        return inventory;
    }

    public String update(int id) {
        inventory.setId(id);
        return "updateRecord";
    }
    
    public String delete(Inventory inv, int id) throws JSONException, IOException {
        inventory.setId(id);
        deleteRecord(inv, inventory.getId());
        return "inventory";
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
                this.inform = "Record deleted!";
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
        ClientResponse response = resource.accept("application/json").get(ClientResponse.class);
        ArrayList<Inventory> inventories = response.getEntity(new GenericType<ArrayList<Inventory>>() {
        });

        for (Inventory u : inventories) {
            if (email.equals(u.getEmail())) {
                inventory.add(u);
            }
        }
        return inventory;
    }

    public int lastId() {
        //retrieve list of all users
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/Inventory_RESTServer/webresources/inventory/allall");
        ClientResponse response = resource.accept("application/json").get(ClientResponse.class);
        ArrayList<Inventory> inventories = response.getEntity(new GenericType<ArrayList<Inventory>>() {
        });
        return inventories.size();
    }

    public String signout() {
        cr.setEmail("");
        user.setEmail("");
        return "index";
    }
}
