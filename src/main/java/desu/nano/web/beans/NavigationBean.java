package desu.nano.web.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean for keeping current page. Can be autowired by another bean.
 *
 * Created by Ker on 19.09.2016.
 */
@ManagedBean(name = "navigation")
@SessionScoped
public class NavigationBean {
    private String currentPage;

    @PostConstruct
    public void init() {
        currentPage = "homePage";
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
