
import java.util.HashMap;

/**
 * Created by lucassilva on 2017-03-30.
 */


public abstract class StatsList {

    HashMap list = new HashMap <String, Integer>();

    public StatsList(){}

    public abstract void insert();

    public HashMap getAll() {
        return list;
    }
}
