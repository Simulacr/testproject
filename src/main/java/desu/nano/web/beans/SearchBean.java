package desu.nano.web.beans;

import com.google.gson.Gson;
import desu.nano.web.objects.SearchResult;
import desu.nano.wiki.Action;
import desu.nano.wiki.Format;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean(name = "search")
@ViewScoped
public class SearchBean implements Serializable{
    private String searchString;
    @ManagedProperty(value = "#{wiki}")
    private WikiBean wiki;
    @ManagedProperty(value = "#{navigation}")
    private NavigationBean navigationBean;
    private SearchResult res;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void searching(ActionEvent actionEvent) {
        try {
            String response = wiki.performAction(Action.opensearch.getQuery(), Format.json.getQuery(),
                    "search=" + URLEncoder.encode(searchString, "UTF-8"));
            List<Object> jsonArray = (List<Object>) new Gson().fromJson(response, Object.class);
            res = new SearchResult((String) jsonArray.get(0), (List<String>) jsonArray.get(1),
                    (List<String>) jsonArray.get(2), (List<String>) jsonArray.get(3));
            navigationBean.setCurrentPage("searching");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getClass().getSimpleName(),
                    ex.getMessage()));
        }
    }

    public void searchingAndGo(ActionEvent actionEvent) {

    }

    public void setWiki(WikiBean wiki) {
        this.wiki = wiki;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public SearchResult getRes() {
        return res;
    }
}
