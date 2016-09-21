package desu.nano.web.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Ker on 20.09.2016.
 */
@ManagedBean(name = "bookmark")
@SessionScoped
public class BookmarkBean implements Serializable{
    private static final String COOKIE_NAME = "bookmarks";
    private Set<String> bookmarks;


    @PostConstruct
    public void initCookie() {
        ExternalContext externalContext =
                FacesContext.getCurrentInstance().getExternalContext();
        Cookie bookmarkCookie = (Cookie)externalContext.getRequestCookieMap().get("bookmarks");
        if(bookmarkCookie != null)
            bookmarks = new HashSet<>(Arrays.asList(bookmarkCookie.getValue().split(URLEncoder.encode(",,,"))));
        else
            bookmarks = new HashSet<>();
    }

    public Set<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<String> bookmarks) {
        this.bookmarks = bookmarks;
    }

    public void mark(String link) {
        if(bookmarks.contains(link))
            bookmarks.remove(link);
        else
            bookmarks.add(link);
    }

    public boolean isInBoolmarks(String link) {
        return bookmarks.contains(link);
    }

    public String genBookmarkMethod(String link) {
        return isInBoolmarks(link)? "removeBookmark('" + link + "');" : "addBookmark('" + link + "');";
    }
}
