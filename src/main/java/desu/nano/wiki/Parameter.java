package desu.nano.wiki;

/**
 * Created by Ker on 19.09.2016.
 */
public interface Parameter {
    String getName();
    String getValue();

    char JOINER = '=';

    default String getQuery() {
        return getName() + JOINER + getValue();
    }
}
