package desu.nano.web.beans;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import desu.nano.web.objects.Article;
import desu.nano.web.objects.ShortLink;
import desu.nano.wiki.Action;
import desu.nano.wiki.Format;
import desu.nano.wiki.RestClient;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean(name = "wiki")
@ViewScoped
public class WikiBean implements Serializable {
    @ManagedProperty(value = "#{restClient}")
    private RestClient restClient;
    @ManagedProperty(value = "#{navigation}")
    private NavigationBean navigationBean;
    @ManagedProperty(value = "#{translator}")
    private Translator translator;
    private List<ShortLink> randomPages = new ArrayList<>();
    private Article currentArticle;

    @PostConstruct
    public void init() {
        randomizePages();
    }

    public String performAction(String... params) {
        String query = String.join("&", params);
        ClientResponse response = restClient.clientGetResponse(translator.getLocale().getLanguage(),
                query);

        if (response.getStatus() != 200) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(String.valueOf(response.getStatus()),
                    "Failed service call: HTTP error code"));
        }

        return response.getEntity(String.class);
    }


    public void randomizePages() {
        try {
            String response = performAction(Action.query.getQuery(), Format.json.getQuery(),
                    "list=random", "rnlimit=10", "rnnamespace=0");
            Map<String, Object> jsonMap = new Gson().fromJson(response, Map.class);
            List<Map> randomPages = extract(jsonMap, "query", "random");// (Map<String, List<Map>>) jsonMap.get("query")).get("random");
            this.randomPages = randomPages.stream().map(i -> new ShortLink(i.get("title").toString())).collect(Collectors.toList());
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getClass().getSimpleName(),
                    ex.getMessage()));
        }
    }



    public List<ShortLink> getRandomPages() {
        return randomPages;
    }

    public void onRandomPage(ActionEvent event) {
        randomizePages();
    }

    public void changeLocution(String newLocution) {
        translator.setLocaleCode(newLocution);
        randomizePages();
        navigationBean.setCurrentPage("homePage");
    }

    public String navigateToArticle(String title) throws UnsupportedEncodingException {
        try {
            String response = performAction(Action.parse.getQuery(), Format.json.getQuery(), "section=0",
                    "prop=text", "page=" + URLEncoder.encode(title, "UTF-8"));
            Map<String, Object> jsonMap = new Gson().fromJson(response, Map.class);

            if(jsonMap.containsKey("error")) {
                Map<String, Object> errorInfo = extract(jsonMap, "error");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorInfo.get("code").toString(),
                        errorInfo.get("info").toString()));
            } else {
                String content = this.<Map<String, Object>>extract(jsonMap, "parse", "text").get("*").toString();
                if (response == null || response.isEmpty())
                    currentArticle = Article.empty();
                else {
                    currentArticle = new Article(title, content, 0);
                }
                navigationBean.setCurrentPage("article");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ex.getClass().getSimpleName(),
                    ex.getMessage()));
        }
        return navigationBean.getCurrentPage();
    }


    @SuppressWarnings("unchecked")
    private <T> T extract(Map<String, Object> map, String... keys) {
        Object value = null;
        for(String key: keys) {
            value = map.get(key);
            if(value instanceof Map)
                map = (Map<String, Object>)value;
            else
                return (T)value;
        }
        return (T)value;
    }

    public String getCssRef() {
        return FacesContext.getCurrentInstance().getExternalContext().getInitParameter("wiki.cssURI." +
                translator.getLocale().getLanguage());
    }

    public Article getCurrentArticle() {
        return currentArticle;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}
