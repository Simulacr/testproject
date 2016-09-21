package desu.nano.web.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ker on 19.09.2016.
 */
public class SearchResult {
    private String requested;
    private List<Link> links = new ArrayList<>();

    public SearchResult(String requested, List<String> headers, List<String> titles, List<String> links) {
        this.requested = requested;
        if(headers == null || headers.size() != titles.size() || headers.size() != links.size())
            throw new RuntimeException();
        for (int i = 0; i < headers.size(); i++)
            this.links.add(new Link(headers.get(i), titles.get(i), links.get(i)));
    }

    public String getRequested() {
        return requested;
    }

    public void setRequested(String requested) {
        this.requested = requested;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public static class Link {
        private final String header;
        private final String description;
        private final String link;

        private Link(String header, String description, String link) {
            this.header = header;
            this.description = description;
            this.link = link;
        }

        public String getHeader() {
            return header;
        }

        public String getDescription() {
            return description;
        }

        public String getLink() {
            return link;
        }
    }
}
