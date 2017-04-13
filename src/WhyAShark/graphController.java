package WhyAShark;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;


/**
 * Created by hahay on 4/11/2017.
 */
public class graphController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart srcPie;

    @FXML
    private PieChart destPie;


    @FXML
    void initialize() {

        assert destPie != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'sample.fxml'.";
        assert srcPie != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'sample.fxml'.";

        String FileName = Controller.getPath();

        PcapParse PcapParse = new PcapParse(FileName);



        Map<String, Integer> IPList2 = PcapParse.getDST().getTopEight();
        Map<String, Integer> IPList3 = PcapParse.getSRC().getTopEight();


        ObservableList<PieChart.Data> pieChartDST = FXCollections.observableArrayList();
        for (String key : IPList2.keySet()) {
            System.out.println( key );
            pieChartDST.add(new PieChart.Data(key, IPList2.get(key)));
        }
        destPie.setData(pieChartDST);



        ObservableList<PieChart.Data> pieChartSRC = FXCollections.observableArrayList();
        for (String key2 : IPList3.keySet()) {
            System.out.println( key2 );
            pieChartSRC.add(new PieChart.Data(key2, IPList3.get(key2)));
        }
        System.out.println(pieChartSRC);
        srcPie.setData(pieChartSRC);
    }
}
