package Controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import entities.Credentials;
import entities.Users;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
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
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    private CloseableHttpClient CLIENT = HttpClients.createDefault();
    public static final String AUTH_SERVICE_PATH = "http://localhost:8080/Inventory_RESTServer/webresources/";

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
    }

    public Users signup(Users user) throws JSONException, IOException {
        try {
            CLIENT = HttpClients.createDefault();
//            LOGGER.debug("RESTAuthBean: 'signup' method called ");
            HttpPost request = new HttpPost(AUTH_SERVICE_PATH + "users/create");
            JSONObject json = new JSONObject();
            json.put("id", user.getId());
            json.put("firstname", user.getFirstname());
            json.put("lastname", user.getLastname());
            json.put("street", user.getStreet());
            json.put("city", user.getCity());
            json.put("province", user.getProvince());
            json.put("country", user.getCountry());
            json.put("postal_code", user.getPostal());
            json.put("email", user.getEmail());
            json.put("password", user.getPassword());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue((EntityUtils.toString(entity)), Users.class);
        } catch (IOException ex) {
//            LOGGER.debug("RESTAuthBean: save user error " + ex.getLocalizedMessage());
        }
        return user;
    }

    public Users findUserByEmail(String email) {

        Users user = null;
        //retrieve list of all users
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource resource = client.resource("http://localhost:8080/Inventory_RESTServer/webresources/users/all");
        ClientResponse response = resource.accept("application/json").get(ClientResponse.class);
        ArrayList<Users> users = response.getEntity(new GenericType<ArrayList<Users>>() {
        });

        for (Users u : users) {
            if (email.equals(u.getEmail())) {
                user = u;
            }
        }
        return user;
    }

    public boolean passwordValidation(Credentials credentials) {

        boolean validator = false;
        //retrieve list of all users
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        String resturl = "http://localhost:8080/Inventory_RESTServer/webresources/users/login";
        WebResource webResource = client.resource(resturl);

        ClientResponse response = webResource.accept("application/json")
                .post(ClientResponse.class, credentials);
        String output = response.getEntity(String.class);

        if (output.equals("SUCCESS! Yuo are logged in!")) {
            validator = true;
        }
        return validator;
    }
}
