
import java.util.HashMap;

/**
 * Created by lucassilva on 2017-03-30.
 */


public abstract class StatsList {

    HashMap list = new HashMap <String, Integer>();

    public StatsList(){}

    public abstract void insert(String ip);

    public HashMap getAll() {
        return list;
    }

    @Override
    public String toString() {
        return "StatsList{" +
                "list=" + list +
                '}';
    }
}
