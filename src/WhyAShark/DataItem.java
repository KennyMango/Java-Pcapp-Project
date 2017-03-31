package WhyAShark;

/**
 * This is a simple class that stores one row of enteredDataItems. We don't know what the row represents - so we just call it
 * a DataItem. Each dataItem, or row, consists of a category and a value, for example: Category="dogs", Value=4.
 *
 * The appropriate getters and setters are defined - but that is all that exists in this class.
 */
public class DataItem {

    private String category;
    private Integer value;

    public DataItem(String category, Integer value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
