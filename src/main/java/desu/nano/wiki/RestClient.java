package desu.nano.wiki;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean
@SessionScoped
public class RestClient {
    private transient Client client;

    public String SERVICE_BASE_URI;

    @PostConstruct
    protected void initialize() {
        FacesContext fc = FacesContext.getCurrentInstance();
        SERVICE_BASE_URI = fc.getExternalContext().getInitParameter("wiki.baseURI");

        client = Client.create();
    }

    public WebResource getWebResource(String relativeUrl) {
        if (client == null) {
            initialize();
        }

        return client.resource(SERVICE_BASE_URI + relativeUrl);
    }

    public ClientResponse clientGetResponse(String relativeUrl) {
        WebResource webResource = client.resource(SERVICE_BASE_URI + relativeUrl);
        webResource.header("Api-User-Agent", "Example/1.0");
        return webResource.accept("application/json").get(ClientResponse.class);
    }
}
