package WhyAShark;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * PieController is responsible for all actions eminating from the PieGraph window.
 */
public class PieController implements Initializable {

    // The @FXML Parent is a link to the object in the fxml file that has fx:id == <var> ... in this case <var> is 'root'.
    @FXML
    Parent root;

    @FXML
    private PieChart chart;
    @FXML
    private Button btnClose;

    /**
     * In order to close the window we need to get a handle to the root of the scene graph. We can then use
     * getScene().getWindow() to get the handle to the window - and then we can close it.
     */
    @FXML
    private void handleCloseButtonAction(ActionEvent e) {
        Stage window = (Stage) root.getScene().getWindow();
        window.close();
    }

    /**
     * When this controller loads (via fxml), we need to set up and display the pie chart. The challenge here is
     * that our saved data is stored in DataItem objects in an arraylist - but we need PieChart.Data objects in an
     * ObservableList. The simplest thing we can do is to iterate over the arraylist and save each dataitem into
     * a new piechart.data object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Retrieve the List of DataItems from the singleton (SharableData).
        List<DataItem> data = ShareableData.getInstance().getDataTable().getContents();

        // Create a new ObservableList of PieChart.Data objects, then iterate and convert the DataItems.
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (DataItem d : data) {
            pieChartData.add(new PieChart.Data(d.getCategory(), d.getValue()));
        }

        // Display the Pie chart.
        chart.setTitle("Categories");
        chart.setData(pieChartData);
    }
}
