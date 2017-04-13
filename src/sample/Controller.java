package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {

    @FXML
    private Button openBut;

    @FXML
    private Button OKBut;

    @FXML
    private TextField textField;

    private static String filePath = "";

    public void openBut(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PCAP files (*.pcap)", "*pcap"));
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            System.out.println(selectedFile.getAbsoluteFile());
            //listView.getItems().add(selectedFile.getAbsoluteFile());
            textField.setText(selectedFile.getAbsolutePath());
            filePath = selectedFile.getAbsoluteFile().toString();
            OKBut.setDisable(false);
        }
        else{
            System.out.println("File is not valid");
        }
    }


    public void OKBut(ActionEvent event){
        //System.out.println(filePath);
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

    public static String getPath(){
        return filePath;
    }

}
