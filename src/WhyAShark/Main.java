package WhyAShark;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WhyAShark - File Opener");
        primaryStage.setScene(new Scene(root, 400, 150));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
