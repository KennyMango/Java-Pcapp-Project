package WhyAShark;

import java.util.ArrayList;
import java.util.List;

/**
 * The DataTable is just a collection of DataItems, ie, an list that stores rows in the Table. We implement our List
 * using an ArrayList - but we keep this implementation private. We can change it to any type of list if we like.
 *
 * Anyway, for this example our enteredDataItems model (in the MVC paradigm) is an arraylist of dataitem objects that
 * implements this:
 *
 *   Category | Value
 *   ----------------
 *   dogs     | 2
 *   cats     | 1
 *   fish     | 12
 *
 *   Of course these are just sample values ... categories and values are whatever you enter ... as long as
 *   category is a string and value is an int.
 */
public class DataTable {

    private List<DataItem> contents = new ArrayList<>();

    public List<DataItem> getContents() {
        return contents;
    }

    public void setContents(List<DataItem> data) {
        this.contents = data;
    }
}
