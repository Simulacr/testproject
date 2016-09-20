package desu.nano.web.objects;

/**
 * Created by Ker on 20.09.2016.
 */
public class Article {
    private String title;
    private String content;
    private long pageId;

    public Article(String title, String content, long pageId) {
        this.title = title;
        this.content = content;
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getPageId() {
        return pageId;
    }

    public static Article empty() {
        return new Article("No title", "No content", 0);
    }
}
