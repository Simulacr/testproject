package desu.nano.web.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Bean that manages wiki bookmarks
 *
 * Created by Ker on 20.09.2016.
 */
@ManagedBean(name = "bookmark")
@SessionScoped
public class BookmarkBean implements Serializable{
    private static final String COOKIE_NAME = "bookmarks";
    private Set<String> bookmarks;


    /*
        Read bookmark cookies on session initialization.
        Display warning message if cookie cannot be readed.
     */
    @PostConstruct
    public void initCookie(){
        FacesContext fc =
                FacesContext.getCurrentInstance();
        Cookie bookmarkCookie = (Cookie)fc.getExternalContext().getRequestCookieMap().get(COOKIE_NAME);
        if(bookmarkCookie != null)
            try {
                bookmarks = new HashSet<>(Arrays.asList(URLDecoder.decode(bookmarkCookie.getValue(), "UTF-8").split(",,,")));
            } catch (UnsupportedEncodingException ex) {
                fc.addMessage(null, new FacesMessage("Warning", "Bookmarks cookie was broken. Bookmarks will be resetted"));
                bookmarks = new HashSet<>();
            }
        else
            bookmarks = new HashSet<>();
    }

    public Set<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<String> bookmarks) {
        this.bookmarks = bookmarks;
    }

    /**
     * @param link - mark/unmark bookmark
     */
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
