package WhyAShark;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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


    String FileName = Controller.getPath();

    PcapParse PcapParse = new PcapParse(FileName);


    public void graphBut(ActionEvent event){
        try{
            FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("graph.fxml"));
            Parent root = fxmlLoader2.load();
            Stage stage = new Stage();

            stage.setTitle("WhyAShark - Top 8 IPs Graphed");
            Scene scene = new Scene(root, 1000, 494);

            stage.setMaxWidth(1000);
            stage.setMaxHeight(494);
            stage.setMinWidth(100);
            stage.setMinHeight(494);
            stage.setScene(scene);


            scene.getStylesheets().add("main.css");
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

    public void fileClose(ActionEvent event){
        Platform.exit();
    }


    public void fileOpen(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PCAP files (*.pcap)", "*pcap"));
        File selectedFile = fc.showOpenDialog(null);


        if(selectedFile != null){
            System.out.println(selectedFile.getAbsoluteFile());
            FileName = selectedFile.getAbsoluteFile().toString();
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("display.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();

                stage.setTitle("Display");
                stage.setScene(new Scene(root1));
                stage.show();
            }catch(Exception e){
                System.out.println("Can't load a new window");
                System.out.println(e);
            }
        }
        else{
            System.out.println("File is not valid");
        }
    }

}
