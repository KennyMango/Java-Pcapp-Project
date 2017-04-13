package WhyAShark;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    public Stage stage = new Stage();

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

            stage.getIcons().add(new Image(getClass().getResourceAsStream("shrimp.png")));
            stage.setMaxWidth(770);
            stage.setMaxHeight(530);
            stage.setMinWidth(770);
            stage.setMinHeight(530);
            stage.setTitle("WhyAShark " +filePath +" - Details");
            Scene scene = new Scene(root1);
            stage.setScene(scene);

            scene.getStylesheets().add("main.css");
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
