package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jnetpcap.Pcap;

import java.io.File;


/**
 * Created by hahay on 4/11/2017.
 */
public class displayController {

    @FXML
    private ListView listView;

    @FXML
    private Button graphBut;

    @FXML
    private Button trafficBut;

    @FXML
    private Button TCPFlagBut;

    @FXML
    private Button clientBut;

    String FileName = Controller.getPath();
    //HashMap<String, Integer> IPList = new HashMap<String, Integer>();
    PcapParse PcapParse = new PcapParse(FileName);


    public void graphBut(ActionEvent event){
        try{
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("graphold.fxml"));
            Parent root = fxmlLoader2.load();
            Stage stage = new Stage();

            stage.setTitle("Graph");
            Scene test = new Scene(root, 1920, 1080);
            stage.setScene(test);

            test.getStylesheets().add("main.css");
            stage.show();
        }catch(Exception e){
            System.out.println("Can't load a new window");
            System.out.println(e);
        }
    }

    public void trafficBut(ActionEvent event){
        trafficBut.setDisable(true);
        listView.getItems().add(PcapParse.getTrafficStats());
    }

    public void TCPFlagBut(ActionEvent event){
        TCPFlagBut.setDisable(true);
        listView.getItems().add(PcapParse.getTCPFlagsStats());
    }

    public void clientBut(ActionEvent event){
        clientBut.setDisable(true);
        listView.getItems().add(PcapParse.getClientPortsUsed());
    }
}
