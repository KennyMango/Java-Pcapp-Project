package WhyAShark;

/**
 * This class implements a Singleton pattern (see Wikipedia Singleton for more info). Recall that a singleton only
 * ever has one instance, and it is globally available.
 *
 * Our singleton is nam,ed ShareableData. It contains a DataTable - which is just a List of DataItems.
 */
public class ShareableData {

    //This statement creates one final static object that is an instance if ourself (this).
    private final static ShareableData instance = new ShareableData();

    // Returns this instance to the caller.
    public static ShareableData getInstance() {
        return instance;
    }

    // Here is the DataTable that is stored in this instance, and a method to getthe DataTable (so we can get or set
    // its values).
    private DataTable dataTable = new DataTable();

    public DataTable getDataTable() {
        return dataTable;
    }
}
