package desu.nano.wiki;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Locale;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean
@SessionScoped
public class RestClient {
    private transient Client client;
    private String defaultLang;

    @PostConstruct
    protected void initialize() {
        client = Client.create();
        defaultLang = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("default.lang");
    }

    private String getServiceUri(String lang) {
        FacesContext fc = FacesContext.getCurrentInstance();
        //TODO check if resource does no exist
        return fc.getExternalContext().getInitParameter("wiki.baseURI." + lang);
    }

    private String getServiceUri() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Locale currentLocale = fc.getViewRoot().getLocale();
        String lang = currentLocale == null ? defaultLang : currentLocale.getLanguage();
        return getServiceUri(lang);
    }
    public WebResource getWebResource(String relativeUrl) {
        if (client == null) {
            initialize();
        }

        return client.resource(getServiceUri() + relativeUrl);
    }

    public ClientResponse clientGetResponse(String lang, String relativeUrl) {
        WebResource webResource = client.resource(getServiceUri(lang) + relativeUrl);
        webResource.header("Api-User-Agent", "Example/1.0");
        return webResource.accept("application/json").get(ClientResponse.class);
    }
}
