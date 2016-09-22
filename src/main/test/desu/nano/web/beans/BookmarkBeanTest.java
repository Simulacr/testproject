package desu.nano.web.beans;

import desu.nano.testutils.ContextMocker;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Ker on 22.09.2016.
 */
public class BookmarkBeanTest {

    @Test
    public void testReadingCookies() throws UnsupportedEncodingException {
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Map<String, Object> cookies = new HashMap<>();
        when(ext.getRequestCookieMap()).thenReturn(cookies);
        when(context.getExternalContext()).thenReturn(ext);
        BookmarkBean bookmarkBean = new BookmarkBean();

        List<String> cookieValues = Arrays.asList("Z", "Some cookie value");
        cookies.put(BookmarkBean.COOKIE_NAME, createCookieValue(cookieValues));
        bookmarkBean.initCookie();
        assertTrue(isAllValuesInSet(bookmarkBean, cookieValues));

        cookies.put(BookmarkBean.COOKIE_NAME, null);
        bookmarkBean.initCookie();
        assertTrue(bookmarkBean.getBookmarks().isEmpty());

        cookieValues = Arrays.asList("悪巫山戯", "Самсинг");
        cookies.put(BookmarkBean.COOKIE_NAME, createCookieValue(cookieValues));
        bookmarkBean.initCookie();
        assertTrue(isAllValuesInSet(bookmarkBean, cookieValues));
    }

    private boolean isAllValuesInSet(BookmarkBean bookmarkBean, List<String> values) {
        Set<String> bookmarks = bookmarkBean.getBookmarks();
        return bookmarks.size() == values.size() && bookmarks.containsAll(values);
    }

    private Cookie createCookieValue(List<String> values) throws UnsupportedEncodingException {
        return new Cookie(BookmarkBean.COOKIE_NAME, URLEncoder.encode(values.stream().
                collect(Collectors.joining(BookmarkBean.COOKIE_SPLITTER)), "UTF-8"));
    }
}
