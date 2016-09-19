package desu.nano.wiki;

/**
 * Created by Ker on 19.09.2016.
 */
public enum Format implements Parameter{
    json("json"), jsonfm("jsonfm"), xml("xml"), xmlfm("xmlfm");

    private String value;
    private static final String PARAMETER_NAME = "format";
    private Format(String value) {
        this.value = value;
    }

    public String getName() {
        return PARAMETER_NAME;
    }

    public String getValue() {
        return value;
    }
}
