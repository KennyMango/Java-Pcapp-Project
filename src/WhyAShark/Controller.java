package WhyAShark;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PieChart pieChart;


    @FXML
    void initialize() {

        assert pieChart != null : "fx:id=\"pieChart\" was not injected: check your FXML file 'sample.fxml'.";
        String FileName = "sample.pcap";
        //HashMap<String, Integer> IPList = new HashMap<String, Integer>();
        PcapDST DST = new PcapDST(FileName);

        DST.readOfflineFiles();

        StatsList DSTList = new StatsList();

        StatsList SRCList = new StatsList();

        for (int i = 0; i < DST.getDST().size(); i++) {

            DSTList.insert(DST.getDST().get(i));


        }


        for (int i = 0; i < DST.getSRC().size(); i++) {

            SRCList.insert(DST.getSRC().get(i));

        }
        //HashMap<String, Double> IPList2 = DSTList.getStats();
        HashMap<String, Integer> IPList3 = SRCList.getStats();
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
        for (String key : IPList3.keySet()) {
            System.out.println( key );
            pieChartData.add(new PieChart.Data(key, IPList3.get(key)));
        }
        pieChart.setData(pieChartData);
    }


}
