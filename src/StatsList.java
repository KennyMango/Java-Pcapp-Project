
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Created by lucassilva on 2017-03-30.
 */


public abstract class StatsList {

    HashMap<String, Integer> list = new HashMap <>();

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
