package desu.nano.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ker on 19.09.2016.
 */
@ManagedBean(name = "translator")
@SessionScoped
public class Translator {
    private String localeCode;
    private static Map<String,Object> countries;
    static{
        countries = new HashMap<>();
        countries.put("English", Locale.ENGLISH); //label, value
        countries.put("日本語", Locale.JAPANESE);
        countries.put("Русский", "ru_RU");
        countries = Collections.unmodifiableMap(countries);
    }
    public String translate(String source) {
        return source;
    }

    public String getLocaleCode() {
        return localeCode;
    }


    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }
}
