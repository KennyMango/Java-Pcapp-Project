package WhyAShark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The InputFormController is responsible for processing events related to the Input Form. Remember, events are things
 * that the user does, such as click a button or press enter or something.
 */
public class InputFormController implements Initializable {

    /**
     * Since the InputFormController has to respond to events, it needs to know what all the controls are on the
     * InputForm. The InputForm layout and controls are all defined in the InputForm.fxml file, and the linkage to
     * the controller is via the fx:id fields. These fx:id fields need to map to variables which are declared here
     * and which have the @FXML annotation.
     *
     * When defining the names I try to use the camel notation, with a three char 'type' as the first word. This helps
     * me remember what type of field each variable corresponds to.
     */
    @FXML
    private TextField txtNewCategory;
    @FXML
    private TextField txtNewValue;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnPie;
    @FXML
    private Button btnBar;
    @FXML
    private Button btnLine;

    /**
     * The first declaration below defines the tableView control and indicates that it will store DataItem objects.
     * The next two lines indicate that the table has two columns, and indicates the first column is in a table of
     * DataItems and stores Strings, and that the second column is also in a table of DataItems but it stores Integers.
     */
    @FXML
    private TableView<DataItem> tblViewDataItems;
    @FXML
    private TableColumn<DataItem, String> colCategory;
    @FXML
    private TableColumn<DataItem, Integer> colValue;

    /**
     * The TableView control displays/stores data from/to a special JavaFX type called an ObservableList. This is
     * a fairly tricky data type in that is a purpose built to do a lot of stuff for us. We are not going to get
     * into what it is exactly - but you should know that of you want get/put stuff in JavaFX controls you need
     * to use these Observable types.
     *
     * This next line declares a ObservableList that we can use to display/get the data in the TableView Control.
     * It is empty to begin with.
     */
    public ObservableList<DataItem> enteredDataItems = FXCollections.observableArrayList();

    /**
     * The initialize() method is required because we chose to 'implement initializable' for this class (see line 27)
     * above. This method runs automatically when the InputForm.fxml is loaded, because this class is specified as
     * the fx:controller in InputForm.fxml. Recall that the fxml is loaded from initRootLayout in Main.java.
     *
     * For this scene we just need to initialize the TableView control, which involves defining the columns for the
     * table, and linking them to our data (the Observable List that we defined above).
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Map the columns to the corresponding field names in DataItem object. We have not covered factory patterns
        // and there is all sorts of fancy stuff happening "under the hood". You just need to know that, for example,
        // the next line is connecting the colCategory column to the category field in a DataItem.
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Make sure the initial TableView has no columns
        tblViewDataItems.getColumns().clear();
        // Associate the actual stored data (if there is any) with the TableView control.
        tblViewDataItems.setItems(enteredDataItems);
        // Add the columns to the TableView
        tblViewDataItems.getColumns().addAll(colCategory, colValue);

    }

    /**
     * When the Add button is pressed, we do this:
     *   - create a new DataItem that contains the current values of the two text input boxes
     *   - add the new DataItem into our ObservableList (which makes it appear on in the table)
     *   - clear the text input boxes
     */
    @FXML
    private void handleAddButtonAction(ActionEvent e) {
        enteredDataItems.add(new DataItem(
                txtNewCategory.getText(),
                Integer.valueOf(txtNewValue.getText())));
        txtNewCategory.clear();
        txtNewValue.clear();
    }

    /**
     * When the PIE button is pressed, we save the current TableView data somewhere so that it can be shared
     * with the PieController. If we had a persistent data store (like a database or file) we would flush the data
     * from the TableView out to the persistent store. But we do not have this, so we need a different solution for
     * this example.
     *
     * Again, to reiterate: we need to put the data somewhere that the other controller can find it. A simple way to
     * do this is to use 'Singleton' object. Singleton is another design pattern that is very common. It's purpose is
     * to create a class that has one and only one instance, and to make that instance globally available to any other
     * threads or objects in the application.
     *
     * In this case we can
     *  - define a singleton that can act as a container for our DataTable object (remember, DataTable is an arraylist
     *    of DataItems)
     *  - whenever we want to share data we ensure that the current TableView contents get stored in the DataTable in
     *    our singleton
     *  - then from the other thread (PieController) we retrieve the DataTable from the singleton, and use it as we
     *    wish
     */
    @FXML
    private void handlePieButtonAction(ActionEvent e) {

        // Save the current TableView data items to the DataTable in the globally available singleton.
        // First we get the DataTable from the singleton (which we names SharableData)...
        DataTable sharedDataTable = ShareableData.getInstance().getDataTable();

        // And now we save the enteredData. Note that there is some implicit type conversion happening: enteredData
        // is an ObservableList, which is a sublinterface if List. DataTable stores List, which happens to be implemented
        // as an ArrayList. But we have hidden this implentation in the DataTable class ... ie ... DataTable just asks
        // for a List object (go have a look in DataTable.java). And since a SharableList is a List, we can pass it as
        // an argument ... and it will be stored as an ArrayList. Coolio! Anyway, let's save the entereddata ...
        sharedDataTable.setContents(enteredDataItems);

        // Now we want to create a completely new window to display the Pie Chart. Our Pie chart window is defined in a
        // different fxml file, and has its own controller. Note that every time this handler runs a new window will be
        // created.
        Stage window;
        VBox root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PieGraph.fxml"));
            root = (VBox) loader.load();

            window = new Stage();
            window.setTitle("MvcExample Chart Display");
            Scene scene = new Scene(root, 400, 400);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void handleBarButtonAction(ActionEvent e) {

        System.out.println("Bar clicked - not yet implemented");

    }

    @FXML
    private void handleLineButtonAction(ActionEvent e) {

        System.out.println("Line clicked - not yet implemeneted");

    }


}
