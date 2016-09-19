package desu.nano.web.beans;

import com.sun.jersey.api.client.ClientResponse;
import desu.nano.wiki.Action;
import desu.nano.wiki.RestClient;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean(name = "wiki")
@ViewScoped
public class WikiBean implements Serializable {
    @ManagedProperty(value = "#{restClient}")
    private RestClient restClient;

    public String performAction(String... params) {
        String query = String.join("&", params);
        ClientResponse response = restClient.clientGetResponse(query);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed service call: HTTP error code : " + response.getStatus());
        }

        return response.getEntity(String.class);
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }
}
