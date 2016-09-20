package desu.nano.web.beans;

import desu.nano.web.objects.SearchResult;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Ker on 20.09.2016.
 */
@ManagedBean(name = "bookmark")
@SessionScoped
public class BookmarkBean implements Serializable{
    private static final String COOKIE_NAME = "bookmarks";
    private List<SearchResult.Link> bookmarks = new ArrayList<>();
    Map<String, Object> properties = new HashMap<>();


    @PostConstruct
    public void initCookie() {
        ExternalContext externalContext =
                FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getRequestCookieMap();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
    }

    public List<SearchResult.Link> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<SearchResult.Link> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public void mark(SearchResult.Link link) {
        if(bookmarks.contains(link))
            bookmarks.remove(link);
        else
            bookmarks.add(link);
        ExternalContext externalContext =
                FacesContext.getCurrentInstance().getExternalContext();
        String cookieValue = bookmarks.stream().map(b -> URLEncoder.encode(b.getLink())).collect(Collectors.joining(";"));
        externalContext.addResponseCookie(COOKIE_NAME, cookieValue, properties);
    }

    public boolean isInBoolmarks(String link) {
        return bookmarks.stream().anyMatch(b -> link.equals(b.getLink()));
    }
}
