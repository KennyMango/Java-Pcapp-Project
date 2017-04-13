package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jnetpcap.Pcap;

import javax.swing.*;
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

    @FXML
    private Button imgBut;

    @FXML
    private Button savetxtBut;




    String FileName = Controller.getPath();

    PcapParse PcapParse = new PcapParse(FileName);


    public void graphBut(ActionEvent event){
        try{
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("graph.fxml"));
            Parent root = fxmlLoader2.load();
            Stage stage = new Stage();

            stage.setTitle("Top 8 Ips");
            Scene test = new Scene(root, 1000, 400);
            stage.setScene(test);

            test.getStylesheets().add("main.css");
            stage.show();
        }catch(Exception e){
            System.out.println("Can't load a new window");
            System.out.println(e);
        }

    }

    public void trafficBut(ActionEvent event){
        listView.getItems().clear();
        listView.getItems().add(PcapParse.getTrafficStats());
    }

    public void TCPFlagBut(ActionEvent event){
        listView.getItems().clear();
        listView.getItems().add(PcapParse.getTCPFlagsStats());
    }

    public void clientBut(ActionEvent event){
        listView.getItems().clear();
        listView.getItems().add(PcapParse.getPortsUsed());
    }

    public void imgBut(ActionEvent event){
        listView.getItems().clear();
        listView.getItems().add(PcapParse.getImageTypes());
    }

    public void savetxtBut(ActionEvent event){


        FileChooser fileChooser = new FileChooser();

        TextArea textArea = new TextArea();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            PcapParse.writeFile(file);
        }

    }

    @FXML
    public void fileClose(ActionEvent event){
        Platform.exit();
    }


}
