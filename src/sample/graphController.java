package sample;

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
    private PieChart pieChart;


    @FXML
    void initialize() {

        assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'sample.fxml'.";
        String FileName = Controller.getPath();
        //HashMap<String, Integer> IPList = new HashMap<String, Integer>();
        PcapParse PcapParse = new PcapParse(FileName);


        //PcapParse.getDST().getData();

        //IpList DSTList = new IpList();

        //IpList SRCList = new IpList();
/*
        for (int i = 0; i < DST.getDST().size(); i++) {

            DSTList.insert(DST.getDST().get(i));


        }


        for (int i = 0; i < DST.getSRC().size(); i++) {

            SRCList.insert(DST.getSRC().get(i));

        }*/
        Map<String, Integer> IPList2 = PcapParse.getDST().getTopEight();

        //HashMap<String, Integer> IPList3 = SRCList.getData();
/*
        for (int i = 0; i < DST.getDST().size(); i++) {
            if (IPList2.containsKey(DST.getDST().get(i))) {
                IPList.put(DST.getDST().get(i), IPList.get(DST.getDST().get(i)) + 1);
            } else {
                IPList.put(DST.getDST().get(i), 0);
            }
        }
*/
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (String key : IPList2.keySet()) {
            System.out.println( key );
            pieChartData.add(new PieChart.Data(key, IPList2.get(key)));
        }
        pieChart.setData(pieChartData);
    }
}
